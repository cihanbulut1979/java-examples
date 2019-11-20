package tr.com.cihan.java.thread.daemon;

public class DaemonTest {
	public static void main(String[] args) {
		Thread t = new Thread() {

			public void run() {
				while (true) {
					System.out.println("Daemon runing");

					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};

		t.setDaemon(true);

		t.start();
	}
}
