package site.metacoding.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HospApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospApplication.class, args);
	}

}
