package tr.com.cihan.java.thread.volatiles;

public class VolatileTest2_Real2 {


	public static void main(String[] args) {
		
		Holder holder = new Holder();
		
		new ChangeListener(holder).start();
		new ChangeMaker(holder).start();
	}

	static class ChangeListener extends Thread {
		private  Holder holder;
		public ChangeListener(Holder holder) {
			this.holder = holder;
		}
		@Override
		public void run() {
			System.out.println("Loooo");
			
			while (true) {
				//1 - bunu yaparsak çalýþýyor
				System.out.println("Looooooooooooooo");
				if(holder.getCounter() > 10){
					System.out.println("Got Change for MY_INT : " + holder.getCounter());
				}
				/*
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				//1 - bunu yaparsak çalýþýyor
				
			}
		}
	}

	static class ChangeMaker extends Thread {
		private  Holder holder;
		public ChangeMaker(Holder holder) {
			this.holder = holder;
		}
		@Override
		public void run() {

			
			while (true) {
				System.out.println("Incrementing MY_INT to :" + (holder.getCounter() + 1));
				holder.setCounter(holder.getCounter() + 1);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class Holder {
		//private volatile int counter;
		private  int counter;

		public Holder() {

		}

		public int getCounter() {
			return counter;
		}

		public void setCounter(int counter) {
			this.counter = counter;
		}

	}
}

