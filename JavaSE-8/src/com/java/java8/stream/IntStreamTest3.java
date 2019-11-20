package com.java.java8.stream;

import java.util.stream.IntStream;

public class IntStreamTest3 {
	public static void main(String[] args) {
		int sum = IntStream.range(1, 10)
		.sum();
		
		System.out.println(sum);
	}
}
