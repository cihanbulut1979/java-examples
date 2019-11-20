package tr.com.cihan.java.io.bytestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ByteTest {
	public static void main(String[] args) throws IOException, URISyntaxException {

		FileOutputStream out = null;

		try {

			out = new FileOutputStream("output-byte.txt");
			
			byte[] bytes = new byte[5];
			
			bytes[0] = 99;
			bytes[1] = 105;
			bytes[2] = 104;
			bytes[3] = 97;
			bytes[4] = 99;

			out.write(bytes);
			
			bytes[0] = 0x63;
			bytes[1] = 0x69;
			bytes[2] = 104;
			bytes[3] = 97;
			bytes[4] = 99;

			out.write(bytes);
			
		} finally {
			
			if (out != null) {
				out.close();
			}
		}
	}
}
