package com.java.java8.stream;

import java.util.Arrays;

public class SimpleStream4 {
	public static void main(String[] args) {
		
		String[] names = {"Cihan","Deniz Ada", "Sanem"};
		
		Arrays.stream(names)
		.filter(value -> value.startsWith("S"))
		.forEach(System.out::println);
	}
}
