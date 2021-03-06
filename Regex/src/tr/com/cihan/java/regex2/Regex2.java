package tr.com.cihan.java.regex2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex2 {
	public static void main(String[] args) {

		String interfaceID = "GigabitEthernet3/0/0/1.200";

		
		//Pattern pattern = Pattern.compile("(.*)");
		Pattern pattern = Pattern.compile("(GigabitEthernet3)?.*");
		
		Matcher matcher = pattern.matcher(interfaceID);
		
		if(matcher.find()){
			for(int i = 0; i< matcher.groupCount(); i++){
				System.out.println(matcher.group(i));
			}
		}
		
		System.out.println("----------------------");
		
		System.out.println(matcher.group(0));
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
		System.out.println(matcher.group(3));
		System.out.println(matcher.group(4));
		System.out.println(matcher.group(5));
		System.out.println(matcher.group(6));
	}
}
