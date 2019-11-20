package tr.com.cihan.java.io.console;

import java.io.IOException;
import java.io.InputStreamReader;

public class SystemIn {
	public static void main(String[] args) throws IOException {
		InputStreamReader cin = new InputStreamReader(System.in);
		
		int data = cin.read();
		while(data != -1){
		    data = cin.read();
		    
		    System.out.println(data);
		}

		cin.close();
		
	}
}
