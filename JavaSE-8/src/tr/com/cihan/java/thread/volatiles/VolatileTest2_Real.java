package tr.com.cihan.java.thread.volatiles;

public class VolatileTest2_Real {


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
			int local_value = holder.getCounter();
			while (local_value < 10) {
				if (local_value != holder.getCounter()) {
					System.out.println("Got Change for MY_INT : " + holder.getCounter());
					local_value = holder.getCounter();
				}
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

			int local_value = holder.getCounter();
			while (holder.getCounter() < 10) {
				System.out.println("Incrementing MY_INT to :" + (local_value + 1));
				holder.setCounter(++local_value);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class Holder {
		private  int counter;
		//volatile

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

