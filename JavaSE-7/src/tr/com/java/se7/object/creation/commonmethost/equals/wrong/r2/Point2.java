package tr.com.java.se7.object.creation.commonmethost.equals.wrong.r2;

public class Point2 {
	private final int x;
	private final int y;

	public Point2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point2))
			return false;
		Point2 p = (Point2) o;
		return p.x == x && p.y == y;
	}
}
