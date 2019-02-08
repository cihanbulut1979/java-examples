
package com.hazelcast;

import java.util.Observer;
import java.util.Set;

import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;

public interface MigratedPartitionListener extends Observer, HazelcastInstanceAware {

	void onMigratedKeySet(IMap<Object, Object> processingMap, Set<Object> migratedKeySet);
	
	String[] processingMaps();

}