package tr.com.cihan.java.thread.synch2;

import java.util.ArrayList;
import java.util.List;

public class MultipleOperationWithNoThreadNoSynchronized {
	
	public List<Integer> list1 = new ArrayList<Integer>();
	public List<Integer> list2 = new ArrayList<Integer>();
	
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
		MultipleOperationWithNoThreadNoSynchronized noThread = new MultipleOperationWithNoThreadNoSynchronized();
	
		long start = System.currentTimeMillis();
		noThread.work();
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));
        System.out.println("List 1'in boyutu: " + noThread.list1.size());
        System.out.println("List 2'nin boyutu: " + noThread.list1.size());
        
        //Ayrýca
        /*Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 15
        at java.util.ArrayList.add(ArrayList.java:459)
        at multithreading.chapter4.Application.addNewIntegerToList2(Application.java:74)
        at multithreading.chapter4.Application.process(Application.java:55)
        at multithreading.chapter4.Application.access$000(Application.java:7)
        at multithreading.chapter4.Application$1.run(Application.java:31)
        at java.lang.Thread.run(Thread.java:745)
    Geçen zaman: 2556
    List 1'in boyutu: 1009
    List 2'nin boyutu 1009*/
		
		
	}

}
