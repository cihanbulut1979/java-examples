package tr.com.java.se7.accessmodifiers.finals;

public final class Complex {
	//private final double re = 0;// init edildiði için tekrar deðer set
								// edilmesine izin vermiyor
	
	private final double re;// init edilmediði için hata vermiyor
	private final double im;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	// Accessors with no corresponding mutators
	public double realPart() {
		return re;
	}

	public double imaginaryPart() {
		return im;
	}

	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	public Complex subtract(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	public Complex divide(Complex c) {
		double tmp = c.re * c.re + c.im * c.im;
		return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Complex))
			return false;
		Complex c = (Complex) o;
		// See page 43 to find out why we use compare instead of ==
		return Double.compare(re, c.re) == 0 && Double.compare(im, c.im) == 0;
	}

	@Override
	public int hashCode() {
		int result = 17 + hashDouble(re);
		result = 31 * result + hashDouble(im);
		return result;
	}

	private int hashDouble(double val) {
		long longBits = Double.doubleToLongBits(re);
		return (int) (longBits ^ (longBits >>> 32));
	}

	@Override
	public String toString() {
		return "(" + re + " + " + im + "i)";
	}

	public static Complex valueOf(double re, double im) {
		return new Complex(re, im);
	}

	public static Complex valueOfPolar(double r, double theta) {
		return new Complex(r * Math.cos(theta), r * Math.sin(theta));
	}

	
	public static void main(String[] args) {
		Complex complex = Complex.valueOf(1.79, 2.3);
		
		complex.hashCode();
		
		complex.add(Complex.valueOf(2.67, 2.367));
	}
}