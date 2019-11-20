package tr.com.cihan.java.thread.reentrant;

public class StarvationTest2 {
	public static void main(String[] args) {
		
		Starvation1 starvation = new Starvation1();

		Thread t1 = new Thread() {
			public void run() {
				starvation.add();
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				starvation.add();
			}
		};

		
		t1.start();
		t2.start();

		
	}
}

class Starvation1 {

	public  synchronized void add() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > ADD");
			try {
				//Thread.yield();
				Thread.sleep(10000);
				//
				//Causes the currently executing thread to sleep 
				//(temporarily cease execution) for the specified number of milliseconds,
				//subject to the precision and accuracy of system timers and schedulers.
				//The thread does not lose ownership of any monitors.
				
				//Baþka bir monitor object üzeirnde iþ yapan threadlerin çalýþmasýna izin veriri
				//Ancak kendi aldýðý monitor object üzerinde lock devam eder
				//
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
