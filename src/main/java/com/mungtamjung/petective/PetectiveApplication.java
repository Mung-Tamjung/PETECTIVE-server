package com.mungtamjung.petective;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PetectiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetectiveApplication.class, args);
	}

}
