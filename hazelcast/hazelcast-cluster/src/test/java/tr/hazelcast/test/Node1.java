package tr.hazelcast.test;

import com.hazelcast.HazelcastConfiguration;

public class Node1 {
	public static void main(String[] args) {
		HazelcastConfiguration.getInstance().init("node-1", true);
	}
}
