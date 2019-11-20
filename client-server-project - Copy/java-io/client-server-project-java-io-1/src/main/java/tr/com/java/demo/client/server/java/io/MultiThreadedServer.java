package tr.com.java.demo.client.server.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedServer implements Runnable {

	protected int serverPort = 8080;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);

	public MultiThreadedServer(int port) {
		this.serverPort = port;
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}

		openServerSocket();

		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					break;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			this.threadPool.execute(new ServerThread(clientSocket));
		}

		this.threadPool.shutdown();

		System.out.println("Server Stopped.");
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			System.out.println("Server Started and listening to the port " + serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + serverPort, e);
		}
	}

	public static class ServerThread extends Thread {
		private Socket socket;

		public ServerThread(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {

			try {

				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String number = br.readLine();
				System.out.println("Server : " + number + " Message received from the client : " + number);

				// Multiplying the number by 2 and forming the return message
				String returnMessage;
				try {
					int numberInIntFormat = Integer.parseInt(number);
					int returnValue = numberInIntFormat * 2;
					returnMessage = String.valueOf(returnValue) + "\n";
				} catch (NumberFormatException e) {
					// Input was not a number. Sending proper message back to
					// client.
					returnMessage = "Please send a proper number\n";
				}

				// Sending the response back to the client.
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(returnMessage);
				bw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		MultiThreadedServer server = new MultiThreadedServer(5454);
		new Thread(server).start();

	}
}
