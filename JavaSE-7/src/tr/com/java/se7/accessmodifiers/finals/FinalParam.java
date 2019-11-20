package tr.com.java.se7.accessmodifiers.finals;

public class FinalParam {
	private int i = 0;
	
	public FinalParam() {
	
	}
	
	public void setI1(final int i1){
		i = i1;
	}
	
	public void setI2(int i1){
		i = i1;
	}
	
	
	public static void main(String[] args) {
		
		FinalParam finalParam= new FinalParam();
		
		for(int i =0; i < 2; i++){
			MyThread myThread = new MyThread(finalParam);
			
			myThread.start();
		}
	}
	
	
	private static class MyThread extends Thread{
		
		private FinalParam finalParam ; 
		
		public MyThread(FinalParam finalParam) {
			this.finalParam = finalParam;
		}
		
		@Override
		public void run() {
			finalParam.setI1(hashCode());
		}
	}
}


