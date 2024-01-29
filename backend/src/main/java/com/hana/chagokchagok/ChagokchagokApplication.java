package com.hana.chagokchagok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChagokchagokApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChagokchagokApplication.class, args);
	}

}
