package tr.com.cihan.java.thread.synch2;

import java.util.ArrayList;
import java.util.List;

public class MultipleOperationWithMultipleThread {
	
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	
	public void add1(Integer int1){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list1.add(int1);
	}
	
	public void add2(Integer int2){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list2.add(int2);
	}
	
	public void process(){

		for(int i =0; i< 1000; i++){
			add1(i);
			add2(i);
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("Baþlýyor ...");
		MultipleOperationWithMultipleThread noThread = new MultipleOperationWithMultipleThread();
	
		long start = System.currentTimeMillis();
		noThread.process();
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken : " + (end - start));
		System.out.println("Bitti ...");
		
		//Time taken : 2189
	}

}
