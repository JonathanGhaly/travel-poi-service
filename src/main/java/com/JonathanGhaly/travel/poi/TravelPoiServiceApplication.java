package com.JonathanGhaly.travel.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class TravelPoiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelPoiServiceApplication.class, args);
	}

}
