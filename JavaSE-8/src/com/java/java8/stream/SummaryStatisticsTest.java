package com.java.java8.stream;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class SummaryStatisticsTest {
	public static void main(String[] args) {
		IntSummaryStatistics summaryStatistics = IntStream.range(1, 10).summaryStatistics();

		System.out.println(summaryStatistics);
	}
}
