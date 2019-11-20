package tr.com.cihan.java.thread.notify;

public class ProducerConsumerExample2 {

	public static void main(String args[]) {
		System.out.println("How to use wait and notify method in Java");
		System.out.println("Solving Producer Consumper Problem");

		int maxSize = 100;

		ProducerConsumer2 producerConsumer = new ProducerConsumer2(maxSize);

		Thread producer1 = new Producer2(producerConsumer, "PRODUCER1");
		Thread producer2 = new Producer2(producerConsumer, "PRODUCER2");
		Thread producer3 = new Producer2(producerConsumer, "PRODUCER3");
		Thread consumer1 = new Consumer2(producerConsumer, "CONSUMER1");
		Thread consumer2 = new Consumer2(producerConsumer, "CONSUMER2");

		producer1.start();
		producer2.start();
		producer3.start();
		consumer1.start();
		consumer2.start();

	}

}

class Producer2 extends Thread {
	private ProducerConsumer2 producerConsumer;

	public Producer2(ProducerConsumer2 producerConsumer, String name) {
		super(name);
		this.producerConsumer = producerConsumer;
	}

	@Override
	public void run() {
		//for(int i =0; i< 10; i++){
		while(true){
			producerConsumer.produce();

		}
	}
}

class Consumer2 extends Thread {
	private ProducerConsumer2 producerConsumer;

	public Consumer2(ProducerConsumer2 producerConsumer, String name) {
		super(name);
		this.producerConsumer = producerConsumer;
	}

	@Override
	public void run() {
		//for(int i =0; i< 10; i++){
		while(true){
			producerConsumer.consume();

		}
	}
}
