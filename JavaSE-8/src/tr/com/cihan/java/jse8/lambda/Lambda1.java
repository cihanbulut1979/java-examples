package tr.com.cihan.java.jse8.lambda;

public class Lambda1 {

	public static void main(String[] args) {
		Foo foo1 = (x, y) -> (2 * x + y);

		int sonuc1 = foo1.apply(3, 4);

		System.out.println("Sonuç: " + sonuc1); // Çýktý: 10

		// veya

		Foo foo2 = (x, y) -> (int) Math.pow(x, y);

		int sonuc2 = foo2.apply(3, 2);

		System.out.println("Sonuç: " + sonuc2); // Çýktý: 3*3 = 9
	}

}

@FunctionalInterface // Opsiyonel
interface Foo {
	int apply(int x, int y);
}
