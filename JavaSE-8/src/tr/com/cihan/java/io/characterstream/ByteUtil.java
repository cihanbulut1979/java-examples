package tr.com.cihan.java.io.characterstream;

import java.io.UnsupportedEncodingException;

public class ByteUtil {

	public static void toBinary(String s, String encoding) throws UnsupportedEncodingException {
		byte[] infoBin = s.getBytes(encoding);
		for (byte b : infoBin) {
			System.out.println("char: " + (b) + " --> " + ((char) b) + "-> " + Integer.toBinaryString(b));
		}
	}
}
