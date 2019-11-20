package tr.com.cihan.java.regex.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordExample {
public static void main(String[] args) {
	   //Pattern pattern = Pattern.compile("[^.]");
	   //. disindaki her harfi tek tek yazar
	   /*
	    Start index: 0 End index: 1 w
		Start index: 1 End index: 2 w
		Start index: 2 End index: 3 w
		Start index: 4 End index: 5 g
		Start index: 5 End index: 6 o
		Start index: 6 End index: 7 o
		Start index: 7 End index: 8 g
		Start index: 8 End index: 9 l
		Start index: 9 End index: 10 e
		Start index: 11 End index: 12 c
		Start index: 12 End index: 13 o
		Start index: 13 End index: 14 m

	    */
	
	//Pattern pattern = Pattern.compile("[^.]+");
	
	/*
	 Start index: 0 End index: 3 www
	Start index: 4 End index: 10 google
	Start index: 11 End index: 14 com

	 */
	
	Pattern pattern = Pattern.compile("[^/]+"); 
	//split(/) gibi. . tayı buluncaya kadar []+ ile devam ettriyor, kelime gibi
	/*
	Start index: 0 End index: 14 www.google.com
	Start index: 15 End index: 24 index.jsp
	*/
	
       Matcher matcher = pattern.matcher("www.google.com/index.jsp");
    
       while (matcher.find()) {
               System.out.print("Start index: " + matcher.start());
               System.out.print(" End index: " + matcher.end() + " ");
               System.out.println(matcher.group());
       }
       
       /* SPLIT ozellikleri
        
        [^/ ]
        [^: ]
        [^, ]
         
        */
}
}
