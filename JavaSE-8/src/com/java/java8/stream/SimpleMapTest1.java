package com.java.java8.stream;

import java.util.Arrays;

public class SimpleMapTest1 {

	public static void main(String[] args) {
		String[] names = {"Cihan","Deniz Ada", "Sanem"};
		
		Arrays.stream(names)
		.map(value -> value + " Bulut")
		.forEach(System.out::println);
		
		
	}
}
