package tr.hazelcast.test;

import com.hazelcast.HazelcastConfiguration;

public class Node2 {
	public static void main(String[] args) {
		HazelcastConfiguration.getInstance().init("node-2", true);
	}
}
