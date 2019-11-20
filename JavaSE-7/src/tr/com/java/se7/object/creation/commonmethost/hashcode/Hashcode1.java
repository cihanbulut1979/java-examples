package tr.com.java.se7.object.creation.commonmethost.hashcode;

import java.util.HashMap;
import java.util.Map;

public class Hashcode1 {
	
	public static void main(String[] args) {
		HashcodeClass1 h11 = new HashcodeClass1(1,"Cihan");
		HashcodeClass1 h12 = new HashcodeClass1(1,"Cihan");
		
		HashcodeClass2 h21 = new HashcodeClass2(1,"Cihan");
		HashcodeClass2 h22 = new HashcodeClass2(1,"Cihan");
		
		HashcodeClass3 h31 = new HashcodeClass3(1,"Cihan");
		HashcodeClass3 h32 = new HashcodeClass3(1,"Cihan");

		
		System.out.println("H11 : " + h11.hashCode());
		System.out.println("H12 : " + h12.hashCode());
		System.out.println("H21 : " + h21.hashCode());
		System.out.println("H22 : " + h22.hashCode());
		System.out.println("H31 : " + h31.hashCode());
		System.out.println("H32 : " + h32.hashCode());
		
		System.out.println("****************************************");
		
		/*
		 H11 : 771517894
		H12 : 394983872
		H21 : 621983827
		H22 : 1879270440
		H31 : 65107054
		H32 : 65107054
		 */
		
		Map map = new HashMap();
		
		map.put(h11, "h11");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h11));
		
		map.put(h12, "h12");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h12));
		
		map.put(h21, "h21");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h21));
		
		map.put(h22, "h22");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h22));
		
		map.put(h31, "h31");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h31));
		
		map.put(h32, "h32");
		System.out.println("map Size : " + map.size());
		System.out.println(map.get(h32));
		
		System.out.println("****************************************");
		
		HashcodeClass1 h111 = new HashcodeClass1(1,"Cihan");
		HashcodeClass1 h121 = new HashcodeClass1(1,"Cihan");
		
		HashcodeClass2 h211 = new HashcodeClass2(1,"Cihan");
		HashcodeClass2 h221 = new HashcodeClass2(1,"Cihan");
		
		HashcodeClass3 h311 = new HashcodeClass3(1,"Cihan");
		HashcodeClass3 h321 = new HashcodeClass3(1,"Cihan");
		
		System.out.println(map.get(h211));		
		System.out.println(map.get(h221));		
		System.out.println(map.get(h311));
		System.out.println(map.get(h321));
		
		
		System.out.println("****************************************");
		
	}
}

class HashcodeClass1 {
	private int id;
	private String name;

	public HashcodeClass1(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class HashcodeClass2 {
	private int id;
	private String name;

	public HashcodeClass2(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof HashcodeClass2) {

			HashcodeClass2 obj2 = (HashcodeClass2) obj;

			if (id == (obj2.getId())) {
				return true;
			}
		}

		return false;

	}

}

class HashcodeClass3 {
	private int id;
	private String name;

	public HashcodeClass3(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof HashcodeClass3) {

			HashcodeClass3 obj2 = (HashcodeClass3) obj;

			if (id == (obj2.getId())) {
				return true;
			}
		}

		return false;

	}

	@Override
	public int hashCode() {

		return 31 * id + name.hashCode();
	}

}
