package com.cpms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CdacPojectManaagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdacPojectManaagementSystemApplication.class, args);
	}

}
