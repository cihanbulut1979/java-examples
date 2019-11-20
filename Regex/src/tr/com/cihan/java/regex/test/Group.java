package tr.com.cihan.java.regex.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Group {
	public static void main(String args[]) {
		// String to be scanned to find the pattern.
		String line = "This order was placed for QT3000! OK?";
		//String pattern = "(.*)(\\d+)(.*)";
		String pattern = "(.*)(\\d+)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		
	
		
		if (m.find()) {
			
			System.out.println(m.group(0));
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			
			for(int i = 0; i< m.groupCount(); i++){
				System.out.println(m.group(i));
			}
		} else {
			System.out.println("NO MATCH");
		}
	}
}
