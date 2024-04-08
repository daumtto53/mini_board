package com.cms.mini_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MiniBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniBoardApplication.class, args);
	}

}
