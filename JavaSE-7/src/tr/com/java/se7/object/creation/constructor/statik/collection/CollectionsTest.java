package tr.com.java.se7.object.creation.constructor.statik.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsTest {
	public static void main(String[] args) {
		Map<String, List<String>> m = CollectionsTest.newInstance();
		
		List<String> list = new ArrayList<String>();
		
		m.put("test", list);
	}

	public static <K, V> HashMap<K, V> newInstance() {
		return new HashMap<K, V>();
	}
}
