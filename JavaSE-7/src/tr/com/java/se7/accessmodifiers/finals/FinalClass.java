package tr.com.java.se7.accessmodifiers.finals;

public final class FinalClass {
	private final int i = 0;
	
	public FinalClass() {
		i = 1;
	}
	
	public void setI1(int i1){
		i = i1;
	}
	
	public void setI2(final int i2){
		i = i2;
	}
}
