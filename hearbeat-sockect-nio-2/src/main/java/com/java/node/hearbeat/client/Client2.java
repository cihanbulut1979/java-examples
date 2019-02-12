package com.java.node.hearbeat.client;

public class Client2 {

    
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
               e.printStackTrace();
            }
        }
        new HeartBeatsClient().connect("Client-2", "127.0.0.1", port);
    }
}
