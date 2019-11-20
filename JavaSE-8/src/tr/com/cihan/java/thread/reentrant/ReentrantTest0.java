package tr.com.cihan.java.thread.reentrant;

public class ReentrantTest0 {
	public static void main(String[] args) {

		final MonitorObject monitorObject = new MonitorObject();

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

class MonitorObject {

	public void add() {
		synchronized (this) {

			while (true) {
				System.out.println(Thread.currentThread().getName() + " - > ADD");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				update();
			}

		}
	}

	public void update() {
		synchronized (this) {
			while (true) {
				System.out.println(Thread.currentThread().getName() + " - > UPDATE");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
