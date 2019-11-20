package tr.com.java.se7.object.creation.commonmethost.equals;

//Broken - violates symmetry!
public final class CaseInsensitiveString1 {
	private final String s;

	public CaseInsensitiveString1(String s) {
		if (s == null)
			throw new NullPointerException();
		this.s = s;
	}

	// Broken - violates symmetry!
	@Override
	public boolean equals(Object o) {
		if (o instanceof CaseInsensitiveString1)
			return s.equalsIgnoreCase(((CaseInsensitiveString1) o).s);
		if (o instanceof String) // One-way interoperability!
			return s.equalsIgnoreCase((String) o);
		return false;
	}
	// Remainder omitted
	
	public static void main(String[] args) {
		CaseInsensitiveString1 cis = new CaseInsensitiveString1("Polish");
		String s = "polish";
		
		if(cis.equals(s)){
			System.out.println("Equalssss");
		}else{
			System.out.println("Not Equalssss");
		}
		
		if(s.equals(cis)){
			System.out.println("Equalssss");
		}else{
			System.out.println("Not Equalssss");
		}
	}
}


