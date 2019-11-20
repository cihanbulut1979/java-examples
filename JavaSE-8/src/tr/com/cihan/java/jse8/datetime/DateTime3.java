package tr.com.cihan.java.jse8.datetime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateTime3 {
	public static void main(String args[]) {
		DateTime3 java8tester = new DateTime3();
		java8tester.testChromoUnits();
	}

	public void testChromoUnits() {

		// Get the current date
		LocalDate today = LocalDate.now();
		System.out.println("Current date: " + today);

		// add 1 week to the current date
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		System.out.println("Next week: " + nextWeek);

		// add 1 month to the current date
		LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
		System.out.println("Next month: " + nextMonth);

		// add 1 year to the current date
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		System.out.println("Next year: " + nextYear);

		// add 10 years to the current date
		LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
		System.out.println("Date after ten year: " + nextDecade);
	}
}
