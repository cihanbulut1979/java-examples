package tr.com.cihan.java.thread.synch;

public class Synchronized1 {
	public static void main(String[] args) {
		
		MyData myData = new MyData();
		
		MyThread21 myThread211 = new MyThread21(myData);
		MyThread21 myThread212 = new MyThread21(myData);
		MyThread21 myThread213 = new MyThread21(myData);
		MyThread21 myThread214 = new MyThread21(myData);
		
		myThread211.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		myThread212.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		myThread213.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		myThread214.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}

class MyThread21 extends Thread{
	
	private MyData myData;
	
	public MyThread21(MyData myData) {
		this.myData = myData;
	}
	
	@Override
	public void run() {
		System.out.println("RUN :" + Thread.currentThread().getName());
		myData.add();
	}
}

class MyData {
	
	public synchronized void add(){
		System.out.println("ADD :" + Thread.currentThread().getName());
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
