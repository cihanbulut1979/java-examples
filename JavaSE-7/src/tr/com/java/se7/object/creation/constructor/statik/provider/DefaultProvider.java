package tr.com.java.se7.object.creation.constructor.statik.provider;

public class DefaultProvider implements Provider {

	@Override
	public Service newService() {

		return new DefaultService();
	}

}
