package tr.com.cihan.java.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex1 {
	public static void main(String[] args) {
		//The metacharacters supported by this API are: <([{\^-=$!|]})?*+.>
		
		//The match still succeeds, even though the dot "." is not present in the input string. 
		//It succeeds because the dot is a metacharacter — a character with special meaning interpreted by the matcher. 
		///The metacharacter "." means "any character" which is why the match succeeds in this example.
		
		/*
		 * 
		 *Construct 	Description
		[abc] 	a, b, or c (simple class)
		[^abc] 	Any character except a, b, or c (negation)
		[a-zA-Z] 	a through z, or A through Z, inclusive (range)
		[a-d[m-p]] 	a through d, or m through p: [a-dm-p] (union)
		[a-z&&[def]] 	d, e, or f (intersection)
		[a-z&&[^bc]] 	a through z, except for b and c: [ad-z] (subtraction)
		[a-z&&[^m-p]] 	a through z, and not m through p: [a-lq-z] (subtraction)
		 */
		
		/*
		 * 
		 * Construct 	Description
		. 	Any character (may or may not match line terminators)
		\d 	A digit: [0-9]
		\D 	A non-digit: [^0-9]
		\s 	A whitespace character: [ \t\n\x0B\f\r]
		\S 	A non-whitespace character: [^\s]
		\w 	A word character: [a-zA-Z_0-9]
		\W 	A non-word character: [^\w]
		 * 
		 */
		
		/*
		 * 
		 * Greedy 	Reluctant 	Possessive 	Meaning
		X? 	X?? 	X?+ 	X, once or not at all
		X* 	X*? 	X*+ 	X, zero or more times
		X+ 	X+? 	X++ 	X, one or more times
		X{n} 	X{n}? 	X{n}+ 	X, exactly n times
		X{n,} 	X{n,}? 	X{n,}+ 	X, at least n times
		X{n,m} 	X{n,m}? 	X{n,m}+ 	X, at least n but not more than m times
		 */
		
		/*
		 * 
		 * Boundary Construct 	Description
		^ 	The beginning of a line
		$ 	The end of a line
		\b 	A word boundary
		\B 	A non-word boundary
		\A 	The beginning of the input
		\G 	The end of the previous match
		\Z 	The end of the input but for the final terminator, if any
		\z 	The end of the input
		 */
		
		/*Matching a Specific Code Point

		You can match a specific Unicode code point using an escape sequence of the form \uFFFF, where FFFF is the hexidecimal value of the code point you want to match. For example, \u6771 matches the Han character for east.

		Alternatively, you can specify a code point using Perl-style hex notation, \x{...}. For example:

		String hexPattern = "\x{" + Integer.toHexString(codePoint) + "}";
		*/
		
		r14();
	}

	private static void r1() {
		Pattern pattern = Pattern.compile("han");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
		
	}
	
	private static void r2() {
		Pattern pattern = Pattern.compile("[han]");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r3() {
		Pattern pattern = Pattern.compile("[^han]");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r4() {
		Pattern pattern = Pattern.compile("[^x]");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r5() {
		Pattern pattern = Pattern.compile("[a-zA-Z]");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r6() {
		Pattern pattern = Pattern.compile("[^a-zA-Z]");
		
		Matcher matcher = pattern.matcher("Cihan");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r7() {
		Pattern pattern = Pattern.compile("[^0-9A-Za-z]");
		
		Matcher matcher = pattern.matcher("%%");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r8() {
		Pattern pattern = Pattern.compile("[^0-9&&A-Z]");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan1");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r9() {
		Pattern pattern = Pattern.compile("\\d");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan12");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r10() {
		Pattern pattern = Pattern.compile("[\\d]");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan12");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r11() {
		Pattern pattern = Pattern.compile("[C+]");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan12");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r12() {
		Pattern pattern = Pattern.compile("[(\\D*)+]{2}");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan12");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r13() {
		/*
		 * Backreferences

		The section of the input string matching the capturing group(s) is saved in memory for later recall via 
		backreference. A backreference is specified in the regular expression as a backslash (\) followed by a 
		digit indicating the number of the group to be recalled. For example, the expression (\d\d) defines
		 one capturing group matching two digits in a row, which can be recalled later in the expression via 
		 the backreference \1.
		
		To match any 2 digits, followed by the exact same two digits, you would use (\d\d)\1 
		as the regular expression:
		
		 
		Enter your regex: (\d\d)\1
		Enter input string to search: 1212
		I found the text "1212" starting at index 0 and ending at index 4.
		
		If you change the last two digits the match will fail:
		
		 
		Enter your regex: (\d\d)\1
		Enter input string to search: 1234
		No match found.

		 */
		Pattern pattern = Pattern.compile("(\\d\\d)\\1");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("3232");
		//Matcher matcher = pattern.matcher("3233");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
	
	private static void r14() {
		Pattern pattern = Pattern.compile("([a-zA-Z]{2})");
		//Pattern pattern = Pattern.compile("([a-zA-Z])");
		//Pattern pattern = Pattern.compile("([a-zA-Z]{2})+");
		//Pattern pattern = Pattern.compile("([a-zA-Z]+)");
		
		//Matcher matcher = pattern.matcher("%%");
		Matcher matcher = pattern.matcher("Cihan12");
		
		while(matcher.find()){
			System.out.println(matcher.group());
			System.out.println(matcher.start());
			System.out.println(matcher.end());
		}
	}
}
