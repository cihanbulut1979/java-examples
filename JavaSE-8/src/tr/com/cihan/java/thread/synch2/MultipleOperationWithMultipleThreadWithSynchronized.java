package tr.com.cihan.java.thread.synch2;

import java.util.ArrayList;
import java.util.List;

public class MultipleOperationWithMultipleThreadWithSynchronized {
	
	public List<Integer> list1 = new ArrayList<Integer>();
	public List<Integer> list2 = new ArrayList<Integer>();
	
	public synchronized void add1(Integer int1){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list1.add(int1);
	}
	
	public synchronized void add2(Integer int2){
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
	
	 public void work() {
	        Thread thread1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                process();
	            }
	        });

	        Thread thread2 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                process();
	            }
	        });

	        thread1.start();
	        thread2.start();

	        try {
	            thread1.join();
	            thread2.join();
	        } catch (InterruptedException e) {
	        }

	    }
	
	public static void main(String[] args) {
		
		System.out.println("Baþlýyor ...");
		MultipleOperationWithMultipleThreadWithSynchronized noThread = new MultipleOperationWithMultipleThreadWithSynchronized();
	
		long start = System.currentTimeMillis();
		noThread.work();
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));
        System.out.println("List 1'in boyutu: " + noThread.list1.size());
        System.out.println("List 2'nin boyutu: " + noThread.list1.size());
        
        /*
         Baþlýyor ...
		Time taken: 4289
		List 1'in boyutu: 2000
		List 2'nin boyutu: 2000

         */
      
        
		
		
	}

}
