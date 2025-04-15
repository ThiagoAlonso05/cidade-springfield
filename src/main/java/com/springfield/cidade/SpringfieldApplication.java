package com.springfield.cidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringfieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringfieldApplication.class, args);
	}

}
