package tr.com.cihan.java.thread.notify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumerInJava {

    public static void main(String args[]) {
        System.out.println("How to use wait and notify method in Java");
        System.out.println("Solving Producer Consumper Problem");
        
        Queue<Integer> buffer = new LinkedList<>();
       
        int maxSize = 10;
        
        Thread producer1 = new Producer(buffer, maxSize, "PRODUCER1");
        Thread producer2 = new Producer(buffer, maxSize, "PRODUCER2");
        Thread producer3 = new Producer(buffer, maxSize, "PRODUCER3");
        Thread consumer1 = new Consumer(buffer, maxSize, "CONSUMER1");
        Thread consumer2 = new Consumer(buffer, maxSize, "CONSUMER2");
        
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        

    }

}


class Producer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;
    
    public Producer(Queue<Integer> queue, int maxSize, String name){
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }
    
    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out .println("Queue is full, "
                                + "Producer thread waiting for "
                                + "consumer to take something from queue");
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                Random random = new Random();
                int i = random.nextInt();
                System.out.println(getName() + " --> Producing value : " + i);
                queue.add(i);
                queue.notifyAll();
            }

        }
    }
}

class Consumer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;
    
    public Consumer(Queue<Integer> queue, int maxSize, String name){
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }
    
    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty,"
                            + "Consumer thread is waiting"
                            + " for producer thread to put something in queue");
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                System.out.println(getName() + " --> Consuming value : " + queue.remove() );
                queue.notifyAll();
            }

        }
    }
}



