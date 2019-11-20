package com.java.java8.stream;

import java.util.stream.IntStream;

public class IntStreamTest2 {
	public static void main(String[] args) {
		IntStream.range(1, 10)
		.skip(5)
		.forEach(System.out::println);
	}
}
