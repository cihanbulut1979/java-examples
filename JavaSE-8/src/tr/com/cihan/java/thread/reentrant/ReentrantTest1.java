package tr.com.cihan.java.thread.reentrant;

public class ReentrantTest1 {
	public static void main(String[] args) {
		
		final MonitorObject1 monitorObject =  new MonitorObject1();
		
		Thread t1 = new Thread(){
			public void run() {
				monitorObject.add();
			}
		};
		
		Thread t2 = new Thread(){
			public void run() {
				monitorObject.update();
			}
		};
		
		Thread t3 = new Thread(){
			public void run() {
				monitorObject.delete();
			}
		};
		
		t1.start();
		t2.start();
		t3.start();
		
		// Thread 1 MonitorObject objesinin synchronized bir metodunda locklý kaldýðý için baþka hiç bir 
		//thread bu objenin baþka bir synchronized metdouna giremiyor 

	}
}

class MonitorObject1 {

	public synchronized void add() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > ADD");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			update();
		}
	}

	public synchronized void update()  {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > UPDATE");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public  void delete()  {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > DELETE");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
