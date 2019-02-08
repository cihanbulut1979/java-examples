
package com.hazelcast;

import com.hazelcast.internal.serialization.InternalSerializationService;
import com.hazelcast.internal.serialization.impl.DefaultSerializationServiceBuilder;

public class HazelcastConfiguration {

	private InternalSerializationService internalSerializationService;

	private HazelcastInstanceWrapper hazelcastInstanceWrapper;

	private HazelcastOperationsFactoryBean hazelcastOperationsFactoryBean;

	private static HazelcastConfiguration instance;

	private static Object lock = new Object();

	private boolean started = false;

	private String hzInstanceId;

	private boolean hotStart;

	public HazelcastConfiguration() {

	}

	private InternalSerializationService hazelcastSerializer(HazelcastInstanceWrapper hazelcastInstance) {
		return new DefaultSerializationServiceBuilder().setAllowUnsafe(true).setEnableCompression(true)
				.setUseNativeByteOrder(true).setHazelcastInstance(hazelcastInstance.hazelcast).build();
	}

	private HazelcastInstanceWrapper hzWrapper() {
		return new HazelcastInstanceWrapper();
	}

	private HazelcastOperationsFactoryBean opsFactory(String hzInstanceId, boolean hotStart,
			HazelcastInstanceWrapper hazelcastInstanceWrapper) {
		return new HazelcastOperationsFactoryBean(hzInstanceId, hotStart, hazelcastInstanceWrapper);
	}

	public InternalSerializationService getInternalSerializationService() {
		return internalSerializationService;
	}

	public HazelcastInstanceWrapper getHazelcastInstanceWrapper() {
		return hazelcastInstanceWrapper;
	}

	public HazelcastOperationsFactoryBean getHazelcastOperationsFactoryBean() {
		return hazelcastOperationsFactoryBean;
	}

	public String getHzInstanceId() {
		return hzInstanceId;
	}

	public void setHzInstanceId(String hzInstanceId) {
		this.hzInstanceId = hzInstanceId;
	}

	public boolean isHotStart() {
		return hotStart;
	}

	public void setHotStart(boolean hotStart) {
		this.hotStart = hotStart;
	}

	public boolean isStarted() {
		return started;
	}

	public static HazelcastConfiguration getInstance() {

		synchronized (lock) {
			if (instance == null) {
				instance = new HazelcastConfiguration();
			}
		}

		return instance;
	}

	public synchronized void init(String hzInstanceId, boolean hotStart) {

		if (!started) {

			this.hzInstanceId = hzInstanceId;
			this.hotStart = hotStart;

			hazelcastInstanceWrapper = hzWrapper();
			internalSerializationService = hazelcastSerializer(hazelcastInstanceWrapper);
			hazelcastOperationsFactoryBean = opsFactory(hzInstanceId, hotStart, hazelcastInstanceWrapper);

			started = true;
		}
	}

}
