package io.github.kawajava.MMOEstateManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MmoEstateManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmoEstateManagerApplication.class, args);
	}

}
