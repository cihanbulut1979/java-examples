package tr.com.cihan.java.thread.deadlock;

public class DeadLockTest {
	public static void main(String[] args) {
		final DeadLockBean bean = new DeadLockBean();

		new Thread() {
			public void run() {

				bean.add();

				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread() {
			public void run() {
				bean.update();

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}

class DeadLockBean {

	public synchronized void add() {

		System.out.println("Add");
		update();

	}

	public synchronized void update() {

		System.out.println("Update");
		add();

	}
}
