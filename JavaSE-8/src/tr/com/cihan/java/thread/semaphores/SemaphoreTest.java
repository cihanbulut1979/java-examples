package tr.com.cihan.java.thread.semaphores;

public class SemaphoreTest {
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore();

		SendingThread sender = new SendingThread(semaphore);

		ReceivingThread receiver = new ReceivingThread(semaphore);

		receiver.start();
		sender.start();
	}
}

class Semaphore {
	private boolean signal = false;

	public synchronized void take() {
		this.signal = true;
		this.notify();
	}

	public synchronized void release() throws InterruptedException {
		while (!this.signal)
			wait();
		this.signal = false;
	}

}

class SendingThread extends Thread{
	Semaphore semaphore = null;

	public SendingThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public void run() {
		while (true) {
			// do something, then signal
			this.semaphore.take();
			System.out.println("TAKEN");
		}
	}
}

class ReceivingThread  extends Thread{
	Semaphore semaphore = null;

	public ReceivingThread(Semaphore semaphore){
	    this.semaphore = semaphore;
	  }

	public void run() {
		while (true) {
			try {
				this.semaphore.release();
				// receive signal, then do something...
				System.out.println("RELEASED");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}