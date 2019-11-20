package tr.com.java.se7.object.creation.commonmethost.equals;

public class Equals1 {
	public static void main(String[] args) {
		EqualClass1 instance11 = new EqualClass1(1, "Cihan");
		EqualClass1 instance12 = new EqualClass1(1, "Cihan");
		
		if(instance11.equals(instance12)){
			System.out.println("Equalssss");
		}else{
			System.out.println("Not Equalssss");
		}
		
		EqualClass2 instance21 = new EqualClass2(1, "Cihan");
		EqualClass2 instance22 = new EqualClass2(1, "Cihan");
		
		if(instance21.equals(instance22)){
			System.out.println("Equalssss");
		}else{
			System.out.println("Not Equalssss");
		}
	}
	
}

class EqualClass1 {
	private int id;
	private String name;

	public EqualClass1(int id, String name) {
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

class EqualClass2 {
	private int id;
	private String name;

	public EqualClass2(int id, String name) {
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
		if(obj == null){
			return false;
		}
		
		if(obj instanceof EqualClass2){
			
			EqualClass2 obj2 =  (EqualClass2) obj;
			
			if(id == (obj2.getId())){
				return true;
			}
		}
		
		return false;
	}
}

