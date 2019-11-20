package com.java.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SimpleReduceTest1 {

	public static void main(String[] args) {
		List<String> stringList = new ArrayList<String>();

		stringList.add("One flew over the cuckoo's nest");
		stringList.add("To kill a muckingbird");
		stringList.add("Gone with the wind");

		Stream<String> stream = stringList.stream();

		Optional<String> reduced = stream.reduce((accumulator, value) -> {
			return accumulator + " + " + value;
		});

		System.out.println(reduced.get());
	}
}
