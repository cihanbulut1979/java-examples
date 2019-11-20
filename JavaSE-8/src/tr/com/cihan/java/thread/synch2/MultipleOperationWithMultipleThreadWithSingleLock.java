package tr.com.cihan.java.thread.synch2;

import java.util.ArrayList;
import java.util.List;

public class MultipleOperationWithMultipleThreadWithSingleLock {

	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();

	private Object lock1 = new Object();

	private void add1(Integer i1) {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

			list1.add(i1);
		}
	}

	private void add2(Integer i2) {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

			list2.add(i2);
		}
	}

	public void process() {

		for (int i = 0; i < 1000; i++) {
			add1(i);
			add2(i);
		}
	}

	public static void main(String[] args) {

		System.out.println("Baþlýyor ...");
		MultipleOperationWithMultipleThreadWithSingleLock noThread = new MultipleOperationWithMultipleThreadWithSingleLock();

		long start = System.currentTimeMillis();
		noThread.process();
		long end = System.currentTimeMillis();

		System.out.println("Time taken: " + (end - start));
        System.out.println("List 1'in boyutu: " + noThread.list1.size());
        System.out.println("List 2'nin boyutu: " + noThread.list1.size());

	
        
        /*
         Ýçsel kilitteki gibi tek kilit olduðu durumu bu yeni yapý ile gözlemlemek amacýyla iki kod bloðunda da örneðin “lock1” kilidini belirtelim:

		...
		
		private void addNewIntegerToList1() {
		    synchronized (lock1) {
		        try {
		            Thread.sleep(1);
		        } catch (InterruptedException e) {
		        }
		
		        list1.add(new Random().nextInt());
		    }
		}
		
		private void addNewIntegerToList2() {
		    synchronized (lock1) {
		        try {
		            Thread.sleep(1);
		        } catch (InterruptedException e) {
		        }
		
		        list2.add(new Random().nextInt());
		    }
		}
		
		Bu durumda, týpký içsel kilitte olduðu gibi, çalýþma süresi tekrar yaklaþýk iki katýna çýkacaktýr:
		
		Geçen zaman: 5049
		List 1'in boyutu: 2000
		List 2'nin boyutu: 2000

         */
	}

}
