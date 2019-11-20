package tr.com.java.se7.object.creation.constructor.statik.provider;

public class ProviderTest {
	public static void main(String[] args) {

		Services.registerDefaultProvider(new DefaultProvider());

		Service service1 = Services.newInstance();
		
		service1.print("hello");

	}
}
