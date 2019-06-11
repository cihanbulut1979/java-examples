package com.hazelcast.job;

import java.util.Set;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.durableexecutor.DurableExecutorService;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;

public class Test {
public static void main(String[] args) {
	String instanceName = "instance-1";
	
	String cron = "0/5 * * * * *";

	Config config = new Config();
	
	 config.setInstanceName(instanceName);
	 config.getDurableExecutorConfig("exec").setCapacity(200).setDurability(2).setPoolSize(8);

	 HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
	 DurableExecutorService executorService = instance.getDurableExecutorService("exec");

	 executorService.executeOnKeyOwner(new EchoTask(instanceName, cron), instanceName);
	
	}
}
