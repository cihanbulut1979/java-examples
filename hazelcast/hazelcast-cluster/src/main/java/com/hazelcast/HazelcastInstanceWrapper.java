
package com.hazelcast;

import java.io.IOException;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;

class HazelcastInstanceWrapper {

	private static final Logger log = LoggerFactory.getLogger(HazelcastInstanceWrapper.class);

	public static final String NODE_INSTANCE_ID = "keyval.hazelcast.id";

	HazelcastInstance hazelcast;

	private String instanceId;

	private volatile boolean started = false;

	private HazelcastClusterListener clusterListener;

	HazelcastInstanceWrapper() {
	}

	private boolean hasInstanceId;

	private void setInstanceProperties(Config hzConfig) {

		hzConfig.setProperty("hazelcast.shutdownhook.enabled", "false");

		hzConfig.setInstanceName(instanceId);
		hzConfig.getMemberAttributeConfig().setStringAttribute(NODE_INSTANCE_ID, instanceId);
		hasInstanceId = true;

	}

	private HazelcastInstance getHazelcastInstance() throws IOException {
		log.info(">> Hazelcast startup sequence initiated");

		Config hzConfig = new XmlConfigBuilder().build();

		setInstanceProperties(hzConfig);

		return Hazelcast.newHazelcastInstance(hzConfig);
	}

	public void start(String instanceId) {
		if (!started) {
			synchronized (this) {
				if (!started) {
					this.instanceId = instanceId;
					start0();
				}
			}
		}

	}

	private void start0() {

		try {

			hazelcast = getHazelcastInstance();
			if (hasInstanceId) {
				verifyInstance();
			}
			joinCluster();
			started = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void verifyInstance() {
		Set<Member> members = hazelcast.getCluster().getMembers();

		int memberIdCnt = 0;
		for (Member m : members) {

			if (m.getStringAttribute(NODE_INSTANCE_ID).equals(getInstanceId())) {
				memberIdCnt++;
			}
			if (memberIdCnt >= 2) {
				stop();
				throw new IllegalStateException(
						"Instance not allowed to join cluster as [" + getInstanceId() + "]. Duplicate instance id.");
			}

		}

	}

	public String getInstanceId() {
		return instanceId;
	}

	public HazelcastClusterListener getClusterListener() {
		return clusterListener;
	}

	@PreDestroy
	public void stop() {
		log.warn("Shutting down Hazelcast instance ..");
		if (hazelcast != null && hazelcast.getLifecycleService().isRunning()) {
			hazelcast.getLifecycleService().shutdown();
			started = false;
		}
	}

	private void joinCluster() {
		log.info("Waiting for node to balance..");
		clusterListener = new HazelcastClusterListener(hazelcast);

		hazelcast.getCluster().addMembershipListener(clusterListener);
		hazelcast.getPartitionService().addMigrationListener(clusterListener);
		hazelcast.getPartitionService().addPartitionLostListener(clusterListener);
		log.info("Registered cluster listeners");

		log.info("---------------------------------------------");
		log.info("Hazelcast system initialized");
		log.info("Joined cluster with groupId [" + hazelcast.getConfig().getGroupConfig().getName() + "] "
				+ " instanceId [" + instanceId + "]");
		log.info("---------------------------------------------");
	}

	public boolean isStarted() {
		return started;
	}

}
