package tr.com.bulutt.java.springframework.circuitbreaber;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@RestController
public class CreditCardController {

	private final FailingRiskClient failingRiskClient;
	private CircuitBreaker evaluate;

	public CreditCardController(CircuitBreakerFactory circuitBreakerFactory, FailingRiskClient failingRiskClient) {
		super();
		this.failingRiskClient = failingRiskClient;
		
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.failureRateThreshold(5)
				.waitDurationInOpenState(Duration.ofMillis(1000))
				.slidingWindowSize(2)
				.build();
		
		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
				.timeoutDuration(Duration.ofMillis(100))
				.build();
		
		circuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder("id").circuitBreakerConfig(
				circuitBreakerConfig).timeLimiterConfig(timeLimiterConfig).build());
		
		this.evaluate = circuitBreakerFactory.create("evaluate");
	}

	@GetMapping("/cards")
	ResponseEntity<List<String>> findAll() {

		List<String> data = new ArrayList<>();

		return ResponseEntity.ok(data);
	}

	@GetMapping("/evaluate")
	String evaluate() {
		return evaluate.run(()-> failingRiskClient.evaluate());
	}
}
