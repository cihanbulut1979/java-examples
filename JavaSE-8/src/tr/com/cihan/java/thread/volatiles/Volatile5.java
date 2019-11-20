package tr.com.cihan.java.thread.volatiles;

import java.util.Scanner;

public class Volatile5 {
	public static void main(String[] args) {
		MyThread5 obj = new MyThread5();
        obj.start();

        Scanner input = new Scanner(System.in);
        input.nextLine(); 
        obj.shutdown();   
    }    
}

class MyThread5 extends Thread {
    private boolean running = true;   //non-volatile keyword

    public void run() {
        while (running) {
            System.out.println("hello");
        }
    }

    public void shutdown() {
        running = false;
    }
}
