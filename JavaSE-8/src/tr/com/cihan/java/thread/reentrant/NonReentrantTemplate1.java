package tr.com.cihan.java.thread.reentrant;

public class NonReentrantTemplate1 {

	public static void main(String[] args) {
		NonReentrantLock template1 = new NonReentrantLock();

		Thread t1 = new Thread() {
			public void run() {
				try {
					template1.lock();
					
					sleep(1000);
					
					template1.unlock();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				
					template1.unlock();
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				

			}
		};

		t1.start();
		t2.start();

	}

}

class NonReentrantLock {
	private boolean isLocked = false;

	public synchronized void lock() throws InterruptedException {
		while (isLocked) {
			wait();
		}
		System.out.println(Thread.currentThread().getName() + " : Locked");
		isLocked = true;
	}

	public synchronized void unlock() {
		System.out.println(Thread.currentThread().getName() + " : UnLocked");
		isLocked = false;
		notify();
	}
}
