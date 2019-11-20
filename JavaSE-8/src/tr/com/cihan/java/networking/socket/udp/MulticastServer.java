package tr.com.cihan.java.networking.socket.udp;

public class MulticastServer {
    public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread().start();
    }
}
