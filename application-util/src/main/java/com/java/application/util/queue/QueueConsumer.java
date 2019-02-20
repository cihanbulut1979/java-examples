package com.java.application.util.queue;

import java.util.concurrent.BlockingQueue;

public class QueueConsumer extends Thread {

    private BlockingQueue<QueueTask> taskQueue = null;
    private volatile boolean       isStopped = false;

    public QueueConsumer(BlockingQueue<QueueTask> queue){
        taskQueue = queue;
    }

    public void run(){
        while(!isStopped()){
            try{
            	QueueTask queueTask = taskQueue.take();
            	queueTask.run();
            	
            	 Thread.sleep(10);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        this.interrupt(); //break pool thread out of dequeue() call.
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }
}