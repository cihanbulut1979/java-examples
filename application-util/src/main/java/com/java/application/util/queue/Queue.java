package com.java.application.util.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {

	private BlockingQueue<QueueTask> taskQueue = null;
	private List<QueueConsumer> threads = new ArrayList<QueueConsumer>();
	private boolean isStopped = false;
	private String name;

	public Queue(String name, int noOfThreads, int maxNoOfTasks) {
		taskQueue = new LinkedBlockingQueue<QueueTask>(maxNoOfTasks);

		for (int i = 0; i < noOfThreads; i++) {
			QueueConsumer consumer = new QueueConsumer(taskQueue);
			consumer.setName("Queue Consumer : " + i);

			threads.add(consumer);
		}
		for (QueueConsumer thread : threads) {
			thread.start();
		}
	}

	public synchronized boolean enqueue(QueueTask task) throws QueueException {
		if (this.isStopped)
			throw new QueueException("Queue is stopped");

		return this.taskQueue.offer(task);

	}

	public synchronized void stop() {
		this.isStopped = true;
		for (QueueConsumer thread : threads) {
			thread.doStop();
		}
	}

	public String getName() {
		return name;
	}

}
