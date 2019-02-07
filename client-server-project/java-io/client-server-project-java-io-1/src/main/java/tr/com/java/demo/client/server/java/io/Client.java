package tr.com.java.demo.client.server.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

	private int port;
	private int clientId;

	public Client(int port, int clientId) {
		this.port = port;
		this.clientId = clientId;
	}

	public void run() {

		Socket socket = null;
		try {
			String host = "localhost";
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);

			// Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			String sendMessage = clientId + "\n";
			bw.write(sendMessage);
			bw.flush();
			System.out.println("Message sent to the server : " + sendMessage);

			// Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println("Client : " + clientId + " Message received from the server : " + message);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < 10; i++) {
			Client client = new Client(5454, i);

			new Thread(client).start();
			
		}
	}
}