package tr.com.cihan.java.thread.reentrant2;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class Runner {

    private int count = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Thread 1 çalýþýyor...");

        condition.await();

        System.out.println("Thread 1 devam ediyor...");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(2000);

        lock.lock();

        System.out.println("Thread 2 çalýþýyor...");
        System.out.print("Devam etmek için 'Enter'a basýnýz: ");

        new Scanner(System.in).nextLine();

        condition.signal();

        System.out.println("Thread 2 devam ediyor...");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
    	
    	Runner  runner = new Runner();
    	
		Thread t1 = new Thread(){
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		
		Thread t2 = new Thread(){
			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Count : " + runner.count);
	}

}
