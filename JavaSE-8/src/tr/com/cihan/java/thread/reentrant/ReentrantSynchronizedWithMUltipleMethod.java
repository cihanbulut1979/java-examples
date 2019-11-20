package tr.com.cihan.java.thread.reentrant;

public class ReentrantSynchronizedWithMUltipleMethod {
	public static void main(String[] args) {
		
		
		Thread t1 = new Thread(){
			public void run() {
				MonitorObject2.add();
			}
		};
		
		
		Thread t2 = new Thread(){
			public void run() {
				MonitorObject2.update();
			}
		};
		
		Thread t3 = new Thread(){
			public void run() {
				MonitorObject2.delete();
			}
		};
		
		t1.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t2.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t3.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Thread 1 MonitorObject objesinin synchronized bir metodunda locklad� ve bu synchronized metdo ba�ka bir 
		//synchronized metodu �a��r�yor. Bu durumda yine ba�ka bir Thread in update i lock lama�na izin vermiyor

	}
}

class MonitorObject2 {

	public static synchronized void add() {
		//while (true) {
			System.out.println(Thread.currentThread().getName() + " - > ADD");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//update();
		//}
	}

	public static synchronized void update()  {
		while (true) {
			System.out.println(Thread.currentThread().getName() + " - > UPDATE");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void delete()  {
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
