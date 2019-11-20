package tr.com.cihan.java.jse8.lambda;

public class Lambda2 {

	public static void main(String[] args) {
		Bar bar = new Bar();

		int sonuc = bar.calculate((x, y) -> (2 * x + y), 3, 4);

		System.out.println("Sonuç: " + sonuc); // Çýktý: 10
	}

}

class Bar {
	public int calculate(Foo foo, int x, int y) {
		return foo.apply(x, y);
	}
}