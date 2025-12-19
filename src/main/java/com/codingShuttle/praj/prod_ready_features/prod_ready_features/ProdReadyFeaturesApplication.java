package com.codingShuttle.praj.prod_ready_features.prod_ready_features;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@SpringBootApplication
public class ProdReadyFeaturesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdReadyFeaturesApplication.class, args);

		//This project stores details of week 4 of prod ready feature lecture.

		//Using generated security password: 1bf0d63e-86a5-484b-81c7-629b1c0855b3

	}

	/*

	Swagger and APi

	http://localhost:8084/v3/api-docs
	http://localhost:8084/swagger-ui/index.html#/post-controller/getAllPosts  -- to showcase to recurter



	 */

}
