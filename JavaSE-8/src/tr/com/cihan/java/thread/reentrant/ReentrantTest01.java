package tr.com.cihan.java.thread.reentrant;

public class ReentrantTest01 {
	public static void main(String[] args) {

		final MonitorObject01 monitorObject = new MonitorObject01();

		Thread t1 = new Thread() {
			public void run() {
				monitorObject.add();
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				monitorObject.update();
			}
		};

		t1.start();
		t2.start();

	}
}

class MonitorObject01 {
	
	private Object obj1 = new Object();
	private Object obj2 = new Object();

	public void add() {
		synchronized (obj1) {

			while (true) {
				System.out.println(Thread.currentThread().getName() + " - > ADD");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//update();
			}

		}
	}

	public void update() {
		synchronized (obj2) {
			while (true) {
				System.out.println(Thread.currentThread().getName() + " - > UPDATE");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
