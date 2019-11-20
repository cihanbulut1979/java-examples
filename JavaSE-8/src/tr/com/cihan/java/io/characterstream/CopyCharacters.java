package tr.com.cihan.java.io.characterstream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters {
	public static void main(String[] args) throws IOException {

		FileReader inputStream = null;
		FileWriter outputStream = null;

		try {
			inputStream = new FileReader("char-in-1.txt");
			outputStream = new FileWriter("char-out-1.txt");

			int c;
			while ((c = inputStream.read()) != -1) {
				outputStream.write(c);
				
				System.out.println(c + " : " + (Integer.toString(c,2)));
				
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}