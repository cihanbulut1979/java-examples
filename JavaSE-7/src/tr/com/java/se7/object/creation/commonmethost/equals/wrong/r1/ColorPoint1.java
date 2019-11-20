package tr.com.java.se7.object.creation.commonmethost.equals.wrong.r1;

import java.awt.Color;

public class ColorPoint1 extends Point1 {
	private final Color color;

	public ColorPoint1(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

	// Broken - violates symmetry!
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ColorPoint1))
			return false;
		return super.equals(o) && ((ColorPoint1) o).color == color;
	}
	
	public static void main(String[] args) {
		Point1 p = new Point1(1, 2);
		ColorPoint1 cp = new ColorPoint1(1, 2, Color.RED);
		
		if(p.equals(cp)){
			System.out.println("Equalss");
		}else{
			System.out.println("Not Equalss");
		}
		
		if(cp.equals(p)){
			System.out.println("Equalss");
		}else{
			System.out.println("Not Equalss");
		}
		//Then p.equals(cp) returns true, while cp.equals(p) returns false
	}
}