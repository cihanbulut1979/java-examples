package tr.com.java.se7.accessmodifiers.hiding;

class DefaultClass {
	public void print1(){
		System.out.println("Print 1");
	}
	
	private void print2(){
		System.out.println("Print 2");
	}
	
	protected void print3(){
		System.out.println("Print 3");
	}
	
	void print4(){
		System.out.println("Print 4");
	}
}
