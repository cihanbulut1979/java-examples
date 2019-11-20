package com.java.java8.stream;

import java.util.Arrays;

public class SimpleMapTest2 {

	public static void main(String[] args) {
		
		Arrays.stream(new int[] {1,3,5,7,9})
		.map(x -> (int)Math.pow(x,2))
		.average().ifPresent(System.out::println);
	}
}
