package tr.com.cihan.java.io.characterstream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CopyLines {
	public static void main(String[] args) throws IOException {

		BufferedReader inputStream = null;
		PrintWriter outputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader("char-in-2.txt"));
			outputStream = new PrintWriter(new FileWriter("char-out-2.txt"));

			String l;
			while ((l = inputStream.readLine()) != null) {
				outputStream.println(l);
				System.out.println(l);
				ByteUtil.toBinary(l, "UTF-8");
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
