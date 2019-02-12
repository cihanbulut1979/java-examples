package com.java.node.heartbeat;

public class PublisherDependencyException extends Exception {
	Exception e = null;

	public PublisherDependencyException() {
		super();
	}

	public PublisherDependencyException(String message) {
		super(message);
	}

	public PublisherDependencyException(Exception erx) {
		super(erx.getMessage());
		e = erx;
	}

	public Exception getNestedException() {
		return e;
	}

}