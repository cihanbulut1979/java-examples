package com.java.java8.stream;

import java.util.stream.Stream;

public class SimpleStream2 {
	public static void main(String[] args) {
		
		Stream.of("Cihan","Deniz Ada", "Sanem")
		.sorted()
		.findFirst()
		.ifPresent(System.out::println);
	}
}
