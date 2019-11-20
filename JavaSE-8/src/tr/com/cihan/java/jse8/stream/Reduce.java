package tr.com.cihan.java.jse8.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class Reduce {
	public static void main(String[] args) {
		String[] myArray = { "this", "is", "a", "sentence" };
		String result = Arrays.stream(myArray).reduce("", (a, b) -> a + b);

		System.out.println(result);

		Stream<String> stream = Arrays.stream(myArray);

		System.out.println(stream);

		String[] myArray1 = { "a", "b", "as", "csdd", "abcde" };

		Stream<String> stream1 = Arrays.stream(myArray1);

		String result1 = stream1.distinct().reduce("", (a, b) -> (a+"," + b));

		System.out.println("result1 : " + result1);

		String[] array =Arrays.stream(myArray1).filter(s -> s.length() > 2).toArray(String[]::new);
		
		System.err.println(Arrays.toString(array));
		
		int myArray3[] = { 1, 5, 8 };
		int sum = Arrays.stream(myArray3).sum();
		
		System.out.println("sum : " + sum);


	}
}
