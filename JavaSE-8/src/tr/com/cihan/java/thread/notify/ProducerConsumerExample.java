package tr.com.cihan.java.thread.notify;

public class ProducerConsumerExample {

	public static void main(String args[]) {
		System.out.println("How to use wait and notify method in Java");
		System.out.println("Solving Producer Consumper Problem");

		int maxSize = 10;

		ProducerConsumer producerConsumer = new ProducerConsumer(maxSize);

		Thread producer1 = new ProducerA(producerConsumer, "PRODUCER1");
		Thread producer2 = new ProducerA(producerConsumer, "PRODUCER2");
		Thread producer3 = new ProducerA(producerConsumer, "PRODUCER3");
		Thread consumer1 = new ConsumerA(producerConsumer, "CONSUMER1");
		Thread consumer2 = new ConsumerA(producerConsumer, "CONSUMER2");

		producer1.start();
		producer2.start();
		producer3.start();
		consumer1.start();
		consumer2.start();

	}

}

class ProducerA extends Thread {
	private ProducerConsumer producerConsumer;

	public ProducerA(ProducerConsumer producerConsumer, String name) {
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

class ConsumerA extends Thread {
	private ProducerConsumer producerConsumer;

	public ConsumerA(ProducerConsumer producerConsumer, String name) {
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
