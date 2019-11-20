package tr.com.java.se7.object.creation.commonmethost.equals;

public final class CaseInsensitiveString2 {
	private final String s;

	public CaseInsensitiveString2(String s) {
		if (s == null)
			throw new NullPointerException();
		this.s = s;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof CaseInsensitiveString2 && ((CaseInsensitiveString2) o).s.equalsIgnoreCase(s);
	}

	public static void main(String[] args) {
		CaseInsensitiveString2 cis = new CaseInsensitiveString2("Polish");
		String s = "polish";

		if (cis.equals(s)) {
			System.out.println("Equalssss");
		} else {
			System.out.println("Not Equalssss");
		}

		if (s.equals(cis)) {
			System.out.println("Equalssss");
		} else {
			System.out.println("Not Equalssss");
		}
		
		CaseInsensitiveString2 cis1 = new CaseInsensitiveString2("Polish");
		
		if (cis.equals(cis1)) {
			System.out.println("Equalssss");
		} else {
			System.out.println("Not Equalssss");
		}
	}
}
