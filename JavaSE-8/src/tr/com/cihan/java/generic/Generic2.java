package tr.com.cihan.java.generic;

import java.util.ArrayList;
import java.util.List;

public interface Generic2 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		list.add("Cihan");

		Generic21 generic21 = new Generic22();

		generic21.set(list);
		
		generic21.unsafeAdd(list, new Integer(45));
		
		generic21.set(list);
	}
}

interface Generic21 {

	public void set(List<?> list);

	public void unsafeAdd(List list, Object o);
}

class Generic22 implements Generic21 {

	@Override
	public void set(List<?> list) {
		for (Object s : list) {
			System.out.println(s);
		}

	}

	public void unsafeAdd(List list, Object o) {
		list.add(o);
	}

}
