package tr.com.cihan.java.thread.notify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumer2 {

	private Queue<Integer> queue = new LinkedList<>();

	private int maxSize;

	public ProducerConsumer2(int maxSize) {
		this.maxSize = maxSize;
	}

	public void produce() {
		synchronized (queue) {

			while (queue.size() == maxSize) {
				try {
					queue.wait();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			Random random = new Random();
			int i = random.nextInt();
			queue.add(i);

			System.out.println(Thread.currentThread().getName() + " --> Producing value : " + i);
			System.out.println(Thread.currentThread().getName() + " --> Queue Size : " + queue.size());

			queue.notifyAll();
		}

	}

	public void consume() {
		synchronized (queue) {
			while (queue.isEmpty()) {

				try {
					queue.wait();

					System.out.println(Thread.currentThread().getName() + " is woken up");

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			System.out.println(Thread.currentThread().getName() + " --> Consuming value : " + queue.remove());
			System.out.println(Thread.currentThread().getName() + " --> Queue Size : " + queue.size());

			queue.notifyAll();
		}

	}

}
