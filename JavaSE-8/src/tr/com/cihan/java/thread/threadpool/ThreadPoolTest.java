package tr.com.cihan.java.thread.threadpool;

public class ThreadPoolTest {
	public static void main(String[] args) {
		final ThreadPool threadPool = new ThreadPool(3, 10000);

		new Thread() {
			@Override
			public void run() {

				for (int i = 0; i < 100; i++) {

					try {
						threadPool.execute(new Task(String.valueOf(i+1)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
