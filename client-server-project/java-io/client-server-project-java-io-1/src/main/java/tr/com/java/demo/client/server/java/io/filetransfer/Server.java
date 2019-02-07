package tr.com.java.demo.client.server.java.io.filetransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends Thread {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final int PORT = 3332;
	public static final int BUFFER_SIZE = 100;

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);

			logger.info("File server is started ..." + serverSocket.getInetAddress() + ":" + PORT);

			while (true) {
				Socket s = serverSocket.accept();
				saveFile(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveFile(Socket socket) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		FileOutputStream fos = null;
		byte[] buffer = new byte[BUFFER_SIZE];

		// 1. Read file name.
		Object o = ois.readObject();
		
		String fileName = null;
		String filePath = null;

		if (o instanceof String) {
			
			fileName = (String)o;
			filePath = "c:/" + fileName;

			logger.info("Server received file : " + fileName);

			File file = new File(filePath);

			if (file.exists()) {
				logger.info("Server received file : " + fileName + " already exist , deleting ...");
				file.delete();
			}

			fos = new FileOutputStream(filePath);
		} else {
			throwException("Something is wrong");
		}

		// 2. Read file to the end.
		Integer bytesRead = 0;

		do {
			o = ois.readObject();

			if (!(o instanceof Integer)) {
				throwException("Something is wrong");
			}

			bytesRead = (Integer) o;

			o = ois.readObject();

			if (!(o instanceof byte[])) {
				throwException("Something is wrong");
			}

			buffer = (byte[]) o;

			// 3. Write data to output file.
			fos.write(buffer, 0, bytesRead);

		} while (bytesRead == BUFFER_SIZE);

		logger.info("Server received file : " + fileName + " transfer success ... ");

		fos.close();

		ois.close();
		oos.close();
	}

	public static void throwException(String message) throws Exception {
		throw new Exception(message);
	}

	public static void main(String[] args) {
		new Server().start();
	}
}