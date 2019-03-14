package br.com.dkzit.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootJpaOneToManyMappingExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaOneToManyMappingExampleApplication.class, args);
	}

}
