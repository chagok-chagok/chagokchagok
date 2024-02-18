package com.hana.chagokchagok;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
public class ChagokchagokApplication {
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Aisa/Seoul"));
		System.out.println("현재시각 : " + new Date());
	}
	public static void main(String[] args) {
		SpringApplication.run(ChagokchagokApplication.class, args);
	}

}
