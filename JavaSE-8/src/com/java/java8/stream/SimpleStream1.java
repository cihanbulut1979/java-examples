package com.java.java8.stream;

import java.util.stream.Stream;

public class SimpleStream1 {
	public static void main(String[] args) {
		
		Stream.of("Cihan","Deniz Ada", "Sanem")
		.sorted()
		.forEach(value -> System.out.println(value));
	}
}
