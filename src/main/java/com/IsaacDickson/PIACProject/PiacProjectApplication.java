package com.IsaacDickson.PIACProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PiacProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiacProjectApplication.class, args);
	}

}
