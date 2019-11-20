package tr.com.cihan.java.jse8.base;

public class Java8Tester3 {
	public static void main(String[] args) {
		Islem toplam = (int a, int b) -> a + b;
		
		
		System.out.println(toplam.topla(1, 2));
		
		Concat concat = (String a, String b) -> a.concat(b);
		
		System.out.println(concat.aa("Cihan", "Bulut"));
		
		Print print = a -> {return a;} ;
		
		System.out.println(print.print("aa"));
		
	}
	
	interface Islem{
		int topla(int a, int b);
		
		//int cikar(int a, int b);
		
		//iki metodu olamaz tek metodu olmalýi
	}
	
	interface Concat{
		String aa(String a, String b);
	}
	
	interface Print{
		String print(String a);
	}
}
