package tr.com.cihan.java.jse8.datetime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class Zone {
	public static void main(String[] args) {
		Set<String> zones = ZoneId.getAvailableZoneIds();
		zones.stream().sorted().forEach(System.out::println);
		
		System.out.println("Default Zone : " + ZoneId.systemDefault());
		
		//-Duser.timezone=Europe/Istanbul
		
		ZonedDateTime.now();
		/* 2014-04-05T16:33:16.320+03:00[Europe/Athens] */
		ZoneId istanbul = ZoneId.of("Europe/Istanbul");
		ZonedDateTime now = ZonedDateTime.now(istanbul);
		
		System.out.println("Now : " + now);
		
		LocalDate localDate = now.toLocalDate(); // 2014-04-05
		
		System.out.println("localDate : " + localDate);
		
		LocalTime localTime = now.toLocalTime(); // 22:33:16.331
		
		System.out.println("localTime : " + localTime);
	}
}
