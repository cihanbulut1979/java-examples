package tr.com.java.se7.object.creation.test;

import java.util.ArrayList;
import java.util.List;

public class ListTest2 {
public static void main(String[] args) {
	long start = System.currentTimeMillis();
	
	List<Integer> list = new ArrayList<Integer>();
	
	Integer deger = null;
	
	for (int i = 0; i < 10000; i++) {
		deger = new Integer(i);
		list.add(deger);
	}
	
	for(int i =0; i< list.size(); i++){
		System.out.println(list.get(i));
	}
	
	long end = System.currentTimeMillis();
	
	System.out.println("Time : " + (end - start) + " ms");
}
}


