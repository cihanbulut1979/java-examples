package tr.com.java.demo.client.server.java.io.filetransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	
	private static Logger logger = LoggerFactory.getLogger(Client.class);
	
	public static void main(String[] args) throws Exception {

		File file = new File("file.txt");
		Socket socket = new Socket("localhost", 3332);
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(file.getName());

		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[Server.BUFFER_SIZE];
		Integer bytesRead = 0;

		while ((bytesRead = fis.read(buffer)) > 0) {
			oos.writeObject(bytesRead);
			oos.writeObject(Arrays.copyOf(buffer, buffer.length));
		}

		oos.close();
		ois.close();
		
		logger.info("Client sent file : " + file.getName() + " transfer success ... ");
		
		System.exit(0);
	}

}
