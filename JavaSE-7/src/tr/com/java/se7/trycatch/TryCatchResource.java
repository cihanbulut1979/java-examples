package tr.com.java.se7.trycatch;

import java.io.IOException;
import java.net.ServerSocket;

public class TryCatchResource {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(9000)) {
			// server socket sanýrým otomatikman finally de close ediliyor

		} catch (IOException e) {
			System.err.println("Could not listen on port " + 9000);
			System.exit(-1);
		}
	}
}
