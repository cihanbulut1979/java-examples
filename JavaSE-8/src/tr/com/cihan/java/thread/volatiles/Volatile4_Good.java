package tr.com.cihan.java.thread.volatiles;

public class Volatile4_Good {
	public static void main(String[] args) {
		
		final Counter4 counter = new Counter4();
		final MyThread4 t1 = new MyThread4(counter);
		final MyThread4 t2 = new MyThread4(counter);

		t1.start();
		t2.start();

		
	}
}

class MyThread4 extends Thread {
	private  Counter4 counter;

	public MyThread4(Counter4 counter) {
		this.counter = counter; 
	}


	@Override
	public void run() {

		for(int i = 0; i< 100; i++){
			counter.increment();
			
			System.out.println(getName() + " -- > " + counter.getCounter());
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Counter4 {
	private  int counter;//fayda etmedi
	//private volatile int counter; //fayda etmedi
	
	public Counter4() {
	}

	public int getCounter() {
		return counter;
	}

	/*public synchronized void  increment() {
		counter ++;
	}*/ 
	//solved problem vithout volatile
	
	public  void  increment() {
		counter ++;
	}
	
	
}
