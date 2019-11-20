package tr.com.java.se7.object.creation.commonmethost.equals.wrong.r1;

public class Point1 {
	private final int x;
	private final int y;

	public Point1(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point1))
			return false;
		Point1 p = (Point1) o;
		return p.x == x && p.y == y;
	}
}
