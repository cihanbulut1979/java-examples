package tr.com.cihan.java.regex.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordExample2 {
public static void main(String[] args) {
	   Pattern pattern = Pattern.compile("\\w+");
	   
       Matcher matcher = pattern.matcher("www.google.com");
    
       while (matcher.find()) {
               System.out.print("Start index: " + matcher.start());
               System.out.print(" End index: " + matcher.end() + " ");
               System.out.println(matcher.group());
       }

       Pattern replace = Pattern.compile("\\s+");
       Matcher matcher2 = replace.matcher("google.com");
       System.out.println(matcher2.replaceAll("\t"));
}
}
