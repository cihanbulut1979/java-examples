package com.java.application.util.queue;

public abstract class QueueTask  implements Runnable{

	public QueueTask() {
	}

	public void run() {
		doExecute();

	}

	public abstract void doExecute();

}
