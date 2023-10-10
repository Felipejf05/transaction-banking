package com.picpay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EntityScan()
@ComponentScan(basePackages = "com.pipcpay")
@SpringBootApplication
public class EndpointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EndpointsApplication.class, args);
	}

}
