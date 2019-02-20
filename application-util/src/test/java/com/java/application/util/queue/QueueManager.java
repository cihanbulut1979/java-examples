package com.java.application.util.queue;

public class QueueManager {

	public static int QUEUE_THREAD_COUNT = 20;
	public static int QUEUE_ELEMENT_COUNT = 1000000;

	private Queue queue;

	private static QueueManager instance;

	private static Object lock = new Object();

	public QueueManager() {
		queue = new Queue("Queue Manager", QUEUE_THREAD_COUNT, QUEUE_ELEMENT_COUNT);
		
	}

	public static QueueManager getInstance() {

		if (instance == null) {
			synchronized (lock) {
				instance = new QueueManager();
			}
		}

		return instance;
	}

	public void enqueue(QueueTask queueTask) throws QueueException {
		boolean queueStatus = queue.enqueue(queueTask);

		if (!queueStatus) {
			throw new QueueException("Queue Task is rejected by queue : " + queueTask);
		}
	}

}
