package com.java.java8.stream;

import java.util.stream.IntStream;

public class IntStreamTest1 {
	public static void main(String[] args) {
		IntStream.range(1, 10).forEach(System.out::println);
		
		IntStream.range(1, 10).forEach(x -> System.out.println(x));
	}
}
