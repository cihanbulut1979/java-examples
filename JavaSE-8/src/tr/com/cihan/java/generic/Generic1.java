package tr.com.cihan.java.generic;

import java.util.ArrayList;
import java.util.List;

public interface Generic1 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		list.add("Cihan");

		Generic11<String> generic11 = new Generic12();

		generic11.set(list);
		
		generic11.unsafeAdd(list, new Integer(45));
		
		generic11.set(list);//Hata !!!!!!!!!!!!!
	}
}

interface Generic11<E> {

	public void set(List<E> list);

	public void unsafeAdd(List list, Object o);
}

class Generic12 implements Generic11<String> {

	@Override
	public void set(List<String> list) {
		for (String s : list) {
			System.out.println(s);
		}

	}

	public void unsafeAdd(List list, Object o) {
		list.add(o);
	}

}
