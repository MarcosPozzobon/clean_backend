package com.marcos.desenvolvimento;

import com.marcos.desenvolvimento.usecases.InsertAdminUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class Startup {

	private final InsertAdminUser insert;

	@Bean
	InitializingBean sendDataBase() {
		return insert::insertAdminUser;
	}

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

}
