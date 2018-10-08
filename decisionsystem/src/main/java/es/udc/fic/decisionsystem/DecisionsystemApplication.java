package es.udc.fic.decisionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DecisionsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecisionsystemApplication.class, args);
	}
}
