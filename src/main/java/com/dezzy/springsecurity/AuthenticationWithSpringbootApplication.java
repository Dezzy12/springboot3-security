package com.dezzy.springsecurity;

import com.dezzy.springsecurity.dto.request.RegistrationRequest;
import com.dezzy.springsecurity.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.dezzy.springsecurity.data.entity.Role.*;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class AuthenticationWithSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationWithSpringbootApplication.class, args);
	}

}
