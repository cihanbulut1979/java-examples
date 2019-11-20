package tr.com.cihan.java.thread.semaphores;

public class BoundedSemaphoreTest {
	public static void main(String[] args) {
		BoundedSemaphore semaphore = new BoundedSemaphore(3);

		SendingThread1 sender = new SendingThread1(semaphore);

		ReceivingThread1 receiver = new ReceivingThread1(semaphore);

		receiver.start();
		sender.start();
	}
}

 class BoundedSemaphore {
	  private int signals = 0;
	  private int bound   = 0;

	  public BoundedSemaphore(int upperBound){
	    this.bound = upperBound;
	  }

	  public synchronized void take() throws InterruptedException{
	    while(this.signals == bound) wait();
	    this.signals++;
	    System.out.println("SIGNAL +" + signals + " , BOUND " + bound);
	    this.notify();
	  }

	  public synchronized void release() throws InterruptedException{
	    while(this.signals == 0) wait();
	    this.signals--;
	    System.out.println("SIGNAL +" + signals + " , BOUND " + bound);
	    this.notify();
	  }
	}

class SendingThread1 extends Thread{
	BoundedSemaphore semaphore = null;

	public SendingThread1(BoundedSemaphore semaphore) {
		this.semaphore = semaphore;
	}

	public void run() {
		while (true) {
			// do something, then signal
			try {
				this.semaphore.take();
				System.out.println("TAKEN");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

class ReceivingThread1  extends Thread{
	BoundedSemaphore semaphore = null;

	public ReceivingThread1(BoundedSemaphore semaphore){
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