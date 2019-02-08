
package com.hazelcast;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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

public class HazelcastOperationsFactoryBean {

	private String hzInstanceId;

	private boolean hotStart;

	private HazelcastInstanceWrapper wrapper;

	private HazelcastOperations hazelcastOperations;

	HazelcastOperationsFactoryBean(String hzInstanceId, boolean hotStart, HazelcastInstanceWrapper wrapper) {
		this.hzInstanceId = hzInstanceId;
		this.hotStart = hotStart;
		this.wrapper = wrapper;
		this.hazelcastOperations = new HazelcastOperationsProxy();

		init();

	}

	private void init() {
		if (hotStart) {
			startWrapper();
		}
	}

	private class HazelcastOperationsProxy implements HazelcastOperations {
		@Override
		public Member thisMember() {
			return getWrapper().hazelcast.getCluster().getLocalMember();
		}

		@Override
		public void set(Object key, Object value, String map) {
			getMap(map).set(key, value);
		}

		@Override
		public Object removeNow(Serializable key, String map) {
			return getMap(map).remove(key);
		}

		@Override
		public boolean removeEntryListener(String id) {
			return getWrapper().hazelcast.removeDistributedObjectListener(id);
		}

		@Override
		public void remove(Object key, String map) {
			getMap(map).removeAsync(key);
		}

		@Override
		public Object put(Object key, Object value, String map) {
			return getMap(map).put(key, value);
		}

		@Override
		public ITopic<?> getTopic(String topic) {
			return getWrapper().hazelcast.getTopic(topic);
		}

		@Override
		public ISet<?> getSet(String set) {
			return getWrapper().hazelcast.getSet(set);
		}

		@Override
		public <K, V> IMap<K, V> getMap(String map) {
			return getWrapper().hazelcast.getMap(map);
		}

		@Override
		public ILock getClusterLock(String name) {
			return getWrapper().hazelcast.getLock(name);
		}

		@Override
		public IAtomicLong getAtomicLong(String key) {
			return getWrapper().hazelcast.getAtomicLong(key);
		}

		@Override
		public Object get(Object key, String map) {
			return getMap(map).get(key);
		}

		@Override
		public boolean contains(Serializable id, String imap) {
			return getMap(imap).containsKey(id);
		}

		@Override
		public String addLocalEntryListener(Serializable keyspace, MapListener listener) {
			return getWrapper().hazelcast.getMap(keyspace.toString()).addLocalEntryListener(listener);
		}

		@Override
		public void addMigrationListener(MigratedPartitionListener listener) {
			listener.setHazelcastInstance(getWrapper().hazelcast);
			getWrapper().getClusterListener().addObserver(listener);
		}

		@Override
		public HazelcastInstance hazelcastInstance() {
			return getWrapper().hazelcast;
		}

		@Override
		public <E> IQueue<E> getQueue(String queue) {
			return getWrapper().hazelcast.getQueue(queue);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <V, K> List<Entry<K, V>> getLocalEntries(String map) {
			Set<K> keys = (Set<K>) getMap(map).localKeySet();
			List<Entry<K, V>> entries = new LinkedList<>();
			for (K key : keys) {
				V val = (V) get(key, map);
				entries.add(new Entry<K, V>() {

					@Override
					public K getKey() {
						return key;
					}

					@Override
					public V getValue() {
						return val;
					}

					@Override
					public V setValue(V arg0) {
						// immutable
						return val;
					}
				});

			}
			return entries;
		}

		@Override
		public IScheduledExecutorService getScheduledExecutorService(String name) {
			return getWrapper().hazelcast.getScheduledExecutorService(name);
		}
	}

	private void startWrapper() {
		if (!getWrapper().isStarted())
			getWrapper().start(hzInstanceId);
	}

	public HazelcastInstanceWrapper getWrapper() {
		return wrapper;
	}

	void setWrapper(HazelcastInstanceWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public String getHzInstanceId() {
		return hzInstanceId;
	}

	public HazelcastOperations getHazelcastOperations() {
		return hazelcastOperations;
	}

}
