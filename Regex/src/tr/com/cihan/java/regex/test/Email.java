package tr.com.cihan.java.regex.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
	public static void main(String[] args) {
		//http://emailregex.com/

		String regex = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";

		String text = "cihan.bulut.1979@gmail.com";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				System.out.println(matcher.group(i));
			}
		}

	}
}
