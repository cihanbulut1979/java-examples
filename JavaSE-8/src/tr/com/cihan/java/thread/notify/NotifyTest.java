package tr.com.cihan.java.thread.notify;

import java.util.ArrayList;
import java.util.List;

public class NotifyTest {
	public static void main(String[] args) {

		Notify1 notify = new Notify1();
		for (int i = 0; i < 1; i++) {
			Producer1 producer = new Producer1(notify);

			producer.start();
		}

		for (int i = 0; i < 10; i++) {
			Consumer1 consumer = new Consumer1(notify);

			consumer.start();
		}
	}
}

class Producer1 extends Thread {

	private Notify1 notify;

	public Producer1(Notify1 notify) {
		this.notify = notify;
	}

	@Override
	public void run() {
		while (true) {
			notify.put();
			

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

class Consumer1 extends Thread {

	private Notify1 notify;

	public Consumer1(Notify1 notify) {
		this.notify = notify;
	}

	@Override
	public void run() {
		while (true) {
			notify.get();
		}

	}
}

class Notify1 {

	public List<String> elements = new ArrayList<String>();

	private boolean signalled = false;

	public Notify1() {

	}

	public void get() {
		synchronized (this) {
			while (!signalled) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (elements.size() > 0) {
				System.out.println("GET :" + Thread.currentThread().getName() + " -" + elements.size());
				elements.remove(0);
			} else {
				signalled = false;
			}

		}

	}

	public void put() {
		synchronized (this) {
			while (true) {
				elements.add(System.currentTimeMillis() + "");
				System.out.println("PUT :" + Thread.currentThread().getName() + " -" + elements.size());
				signalled = true;
				notifyAll();

			}
		}
	}
}
