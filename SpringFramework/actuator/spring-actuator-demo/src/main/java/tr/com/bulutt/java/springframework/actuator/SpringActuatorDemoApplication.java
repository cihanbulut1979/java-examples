package tr.com.bulutt.java.springframework.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringActuatorDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringActuatorDemoApplication.class, args);
	}
	
	@RestController
	@RequestMapping("/check")
	class TestController {
		
		@GetMapping("/get")
		public String get() {
			return "get returned";
		}
	}
	
	@Service
	@Endpoint(id="random")
	class RandomEndpoint{
		
		String data = "1.";
		
		@ReadOperation
		public String read() {
			return data;
		}
		
		@WriteOperation
		public void write(String data){
			this.data = data;
		}
	}

}
