package com.java.java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileStreamTest2 {
	public static void main(String[] args) throws IOException {
		Stream<String> rows = Files.lines(Paths.get("input.txt"));

		long c = rows.count();
		
		System.out.println(c);
		
		rows.close();
	}
}
