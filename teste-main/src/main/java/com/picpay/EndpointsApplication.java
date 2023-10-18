package com.picpay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.picpay")
@EntityScan(basePackages = "com.picpay.dataprovider.database.entity")
@ComponentScan(basePackages = "com.picpay")
public class EndpointsApplication {


	public static void main(String[] args) {
		SpringApplication.run(EndpointsApplication.class, args);
	}

}
