package tr.com.java.se7.object.creation.slowest.longs;

public class PrimitiveLongTest {
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time : " + (end - start) + " ms");
	}
}
