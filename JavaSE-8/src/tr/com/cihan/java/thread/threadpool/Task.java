package tr.com.cihan.java.thread.threadpool;

public class Task implements Runnable {

	private String taskId;

	public Task(String taskId) {
		super();
		this.taskId = taskId;
	}

	@Override
	public void run() {

		System.out.println("Task : " + taskId + " executed by " + Thread.currentThread().getName());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
