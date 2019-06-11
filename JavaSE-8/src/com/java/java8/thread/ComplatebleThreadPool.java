package com.java.java8.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComplatebleThreadPool {
	public static void main(String[] args) {

		System.out.println("Started");

		ExecutorService taskExecutor = Executors.newFixedThreadPool(4);

		for (int i = 0; i < 10; i++) {

			try {

				taskExecutor.execute(() -> {
					System.out.println(Thread.currentThread().getName() + " running");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		taskExecutor.shutdown();
		try {
			taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Completed");

	}
}
