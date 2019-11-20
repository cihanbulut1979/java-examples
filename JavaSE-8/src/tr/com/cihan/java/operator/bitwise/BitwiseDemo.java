package tr.com.cihan.java.operator.bitwise;

public class BitwiseDemo {
	public static void main(String[] args) {
		int a = 60;
		
		String binary1 = Integer.toBinaryString(a);
		System.out.println("A: "+binary1);
		//As binary: 111100
		
		int b = 13;
		
		String binary2 = Integer.toBinaryString(b);
		System.out.println("B: "+binary2);
		//As binary: 1101
		
		String binary3 = Integer.toBinaryString(a&b);
		System.out.println("A&B: "+binary3);
		System.out.println(a&b);
		//As binary: 1100
		//AND
		
		String binary4 = Integer.toBinaryString(a|b);
		System.out.println("A|B: "+binary4);
		System.out.println(a|b);
		//As binary: 111101
		//OR
		
		String binary5 = Integer.toBinaryString(a^b);
		System.out.println("A^B: "+binary5);
		System.out.println(a^b);
		//XOR
		//110001
		//49
	
		String binary6 = Integer.toBinaryString(~a);
		System.out.println("~A: "+binary6);
		System.out.println(~a);
		//Complenet of A : A nýn tersi 1 --> 0 , 0 --> 1
		//11111111111111111111111111000011
		
		String binary7 = Integer.toBinaryString(a>>2);
		System.out.println("a>>2: "+binary7);
		System.out.println(a>>2);
		//Shift Right A 2 bit Saða doðru kaydýrdý en saðdaki 2 bit düþtü. En sola 2 tane 0 ekledi
		//1111
		//15
		
		String binary8 = Integer.toBinaryString(a<<2);
		System.out.println("a<<2: "+binary8);
		System.out.println(a<<2);
		//Shift Right A 2 bit   sola doðru kaydýrdýen saða 2 tane 0 bit eklendi
		//11110000
		//240
		
		String binary9 = Integer.toBinaryString(a>>>2);
		System.out.println("a>>>2: "+binary9);
		System.out.println(a>>>2);
		//1111
		//15
	
		int x = 10;
		
		System.out.println(x++);// x i once yazdi sonra arttirdi
		System.out.println(--x);// once azaltti sonra yazdirdi
		
		
		
	}
}
