package tr.com.cihan.java.io.console;

import java.util.Locale;

public class Scanner {
	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		try {
			scanner.useLocale(new Locale("tr", "TR"));

			while (scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
}
