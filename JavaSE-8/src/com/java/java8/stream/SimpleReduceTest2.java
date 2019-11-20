package com.java.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleReduceTest2 {

	public static void main(String[] args) {

		String[] names = { "Cihan Bulut 1079 ORDU", "Sanem Bulut 1981 Ankara", "Deniz Ada Bulut 2015 Ankara" };

		String[] init = new String[0];
		
		String[] result = Arrays.stream(names).map(x -> x.split(" ")).reduce(init, (accumulator, value) -> {
			
			List<String> l1 = new ArrayList<>();
			
			Collections.addAll(l1, accumulator);
			Collections.addAll(l1, value);
			
			return l1.toArray(new String[l1.size()]);
		});

		System.out.println(result);

	}
}
