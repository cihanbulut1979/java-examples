
package com.hazelcast;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ISet;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Member;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;

public interface HazelcastOperations {

	HazelcastInstance hazelcastInstance();	
	
	void remove(Object key, String map);

	<K, V> IMap<K, V> getMap(String map);
	
	Object put(Object key, Object value, String map);

	void set(Object key, Object value, String map);

	Object get(Object key, String map);
	
	<V, K> List<Entry<K, V>> getLocalEntries(String map);	
	
	String addLocalEntryListener(Serializable keyspace, MapListener listener);
	
	boolean removeEntryListener(String id);

	Object removeNow(Serializable key, String map);

	ILock getClusterLock(String name);

	boolean contains(Serializable id, String imap);

	ITopic<?> getTopic(String topic);
	
	IAtomicLong getAtomicLong(String key);

	ISet<?> getSet(String set);

	Member thisMember();
	
	<E> IQueue<E> getQueue(String queue);
	
	void addMigrationListener(MigratedPartitionListener listener);
	
	IScheduledExecutorService getScheduledExecutorService(String name);

}