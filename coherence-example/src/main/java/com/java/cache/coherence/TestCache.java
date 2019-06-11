package com.java.cache.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class TestCache {
	public static void main(String[] args) {

		CacheFactory.ensureCluster();
		NamedCache cache = CacheFactory.getCache("hello-example");

		for (int i = 0; i < 1000; i++) {
			String key = "k" + i;
			String value = "Hello World " + i;
			cache.put(key, value);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println((String) cache.get(key) + " --> " + cache.getCacheService().getCluster().getLocalMember().getUid());
			
			

		}

		CacheFactory.shutdown();
	}
}
