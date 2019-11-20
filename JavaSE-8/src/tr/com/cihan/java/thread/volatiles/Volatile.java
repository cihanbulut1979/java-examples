package tr.com.cihan.java.thread.volatiles;

public class Volatile {
	public static void main(String[] args) {
		final MyThread t = new MyThread();

		t.start();

	}
}

class MyThread extends Thread {
	private int counter;

	public MyThread() {

	}


	@Override
	public void run() {

		for(int i = 0; i< 100; i++){
			counter ++;
			
			System.out.println(getName() + " -- > " + counter);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getCounter() {
		return counter;
	}

}
