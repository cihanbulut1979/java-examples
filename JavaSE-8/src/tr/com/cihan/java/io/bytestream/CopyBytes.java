package tr.com.cihan.java.io.bytestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class CopyBytes {
	public static void main(String[] args) throws IOException, URISyntaxException {

		FileInputStream in = null;
		FileOutputStream out = null;

		try {

			in = new FileInputStream("input.txt");
			out = new FileOutputStream("output.txt");
			int c;

			while ((c = in.read()) != -1) {
				out.write(c);
				
				System.out.println(c + " : " + (Integer.toString(c,2)));
				// c decimal deðerdir 
				// c için decimal 99, hex 63 ( 6*16^1 + 3 * 16^0), binary 01100011 (0 + 64 + 32 + 0 + 0 + 0 + 0 + 2 4 1)
				
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
