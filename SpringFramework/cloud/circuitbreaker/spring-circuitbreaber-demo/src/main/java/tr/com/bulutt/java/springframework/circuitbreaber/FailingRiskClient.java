package tr.com.bulutt.java.springframework.circuitbreaber;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class FailingRiskClient {

	public String evaluate() {
		try {
			TimeUnit.MICROSECONDS.sleep(300);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(new Random().nextBoolean()) {
			throw new IllegalStateException();
		}
		
		if(new Random().nextBoolean()) {
			throw new IllegalStateException();
		}
		
		return "OK";
	}
}
