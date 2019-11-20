package tr.com.cihan.java.generic;

import java.util.ArrayList;
import java.util.List;

public interface Generic3 {
	public static void main(String[] args) {
		List<MyObject> list = new ArrayList<MyObject>();

		list.add(new MyObject("Cihan"));

		Generic32 generic21 = new Generic32();

		generic21.set(list);

		generic21.unsafeAdd(list, new Integer(45));

		generic21.set(list);
		
		generic21.set1(list);
		
	}
}

class Generic32 {

	public <E> void set(List<E> list) {
		for (E s : list) {
			System.out.println(s);
		}

	}
	
	public <E extends MyObject> void set1(List<E> list) {
		for (E s : list) {
			System.out.println(s.getName());
		}

	}

	public void unsafeAdd(List list, Object o) {
		list.add(o);
	}

}

class MyObject {
	private String name;

	public MyObject(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
