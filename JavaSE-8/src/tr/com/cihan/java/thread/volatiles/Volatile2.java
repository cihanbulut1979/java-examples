package tr.com.cihan.java.thread.volatiles;

public class Volatile2 {
	public static void main(String[] args) {

		final Counter counter = new Counter();
		final MyThread2 t = new MyThread2(counter);

		t.start();

	}
}

class MyThread2 extends Thread {
	private Counter counter;

	public MyThread2(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void run() {

		for (int i = 0; i < 100; i++) {
			counter.increment();
			
			System.out.println(counter.getCounter());

			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Counter {
	private int counter;

	public Counter() {
	}

	public int getCounter() {
		return counter;
	}

	public void increment() {
		counter++;
	}

}
