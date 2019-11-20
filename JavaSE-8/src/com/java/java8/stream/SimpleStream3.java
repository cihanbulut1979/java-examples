package com.java.java8.stream;

import java.util.Arrays;

public class SimpleStream3 {
	public static void main(String[] args) {
		
		String[] names = {"Cihan","Deniz Ada", "Sanem"};
		
		//Stream.of(names)
		Arrays.stream(names)
		.sorted()
		.findFirst()
		.ifPresent(System.out::println);
		
	}
}
