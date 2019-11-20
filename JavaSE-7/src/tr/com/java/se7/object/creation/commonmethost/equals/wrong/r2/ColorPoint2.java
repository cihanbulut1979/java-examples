package tr.com.java.se7.object.creation.commonmethost.equals.wrong.r2;

import java.awt.Color;

public class ColorPoint2 extends Point2 {
	private final Color color;

	public ColorPoint2(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}

	// Broken - violates transitivity!
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point2))
			return false;
		// If o is a normal Point, do a color-blind comparison
		if (!(o instanceof ColorPoint2))
			return o.equals(this);
		// o is a ColorPoint; do a full comparison
		return super.equals(o) && ((ColorPoint2) o).color == color;
	}

	public static void main(String[] args) {
		ColorPoint2 p1 = new ColorPoint2(1, 2, Color.RED);
		Point2 p2 = new Point2(1, 2);
		ColorPoint2 p3 = new ColorPoint2(1, 2, Color.BLUE);

		if (p1.equals(p2)) {
			System.out.println("Equalss");
		} else {
			System.out.println("Not Equalss");
		}

		if (p1.equals(p2)) {
			System.out.println("Equalss");
		} else {
			System.out.println("Not Equalss");
		}
		
		if (p2.equals(p3)) {
			System.out.println("Equalss");
		} else {
			System.out.println("Not Equalss");
		}
		
		if (p1.equals(p3)) {
			System.out.println("Equalss");
		} else {
			System.out.println("Not Equalss");
		}
		
		//Now p1.equals(p2) and p2.equals(p3) return true, while p1.equals(p3)
			//	returns false, a clear violation of transitivity. The first two comparisons are
				//“color-blind,” while the third takes color into account.
	}
}