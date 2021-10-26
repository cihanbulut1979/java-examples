package tr.com.java.spring.batch.remotepartioning;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.item.ExecutionContext;


public class BasicPartitioner extends SimplePartitioner {
	
	private static List<String> data = new ArrayList<String>();
	
	static {
		for(int i =0; i< 100; i++) {
			data.add(String.valueOf("Data-" + i));
		}
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitions = new HashMap<>();
					
		for(int i=0; i< data.size(); i++) {
			
			String partitionId = "partition" + i;
			
			String value = data.get(i);
			
			ExecutionContext context = new ExecutionContext();
			
			context.put("id", i);
			context.put("value", value);
			context.put("partitionId", partitionId);
			
			partitions.put(partitionId, context);
		}
			
		
		return partitions;

	}

}
