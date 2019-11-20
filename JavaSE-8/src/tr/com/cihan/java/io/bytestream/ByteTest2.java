package tr.com.cihan.java.io.bytestream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ByteTest2 {
	public static void main(String[] args) throws IOException, URISyntaxException {

		FileOutputStream out = null;

		try {

			out = new FileOutputStream("output-byte-2.txt");
			
			out.write(new String("Ý").getBytes());
			
		} finally {
			
			if (out != null) {
				out.close();
			}
		}
	}
}
