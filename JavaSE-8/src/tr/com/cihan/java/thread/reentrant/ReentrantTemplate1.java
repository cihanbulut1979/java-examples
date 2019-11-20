package tr.com.cihan.java.thread.reentrant;

public class ReentrantTemplate1 {

	  public synchronized void outer(){
	    inner();
	  }

	  public synchronized void  inner(){
	    //do something
	  }
}
