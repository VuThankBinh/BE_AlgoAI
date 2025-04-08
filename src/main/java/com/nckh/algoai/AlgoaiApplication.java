package com.nckh.algoai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlgoaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgoaiApplication.class, args);
	}

}
