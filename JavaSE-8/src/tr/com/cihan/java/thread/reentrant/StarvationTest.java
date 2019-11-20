package tr.com.cihan.java.thread.reentrant;

public class StarvationTest {
	public static void main(String[] args) {
		
		Starvation starvation = new Starvation();

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

		Thread t3 = new Thread() {
			public void run() {
				starvation.yield();
			}
		};
		
		Thread t4 = new Thread() {
			public void run() {
				starvation.yield();
			}
		};
		
		Thread t5 = new Thread() {
			public void run() {
				starvation.test();
			}
		};

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

		// Yine buradada sadece Thread 1 �al���yor. Di�er thread lere izin vebrmiyor o class�n synchronized bloklar�ndan dolay� m� ?

	}
}

class Starvation {

	public  synchronized void add() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > ADD");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public  synchronized void yield() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > YIELD");

			Thread.yield();

		}
	}
	
	public  void test() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > TEST");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
