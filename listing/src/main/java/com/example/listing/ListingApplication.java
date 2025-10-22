package com.example.listing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {
        "com.example.listing"
})
@EntityScan(basePackages = {
        "com.example.listing.model"
})
@EnableCaching
public class ListingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListingApplication.class, args);
	}

}
