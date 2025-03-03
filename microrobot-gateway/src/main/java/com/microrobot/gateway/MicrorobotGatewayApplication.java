package com.microrobot.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.microrobot.gateway.client")
@SpringBootApplication
public class MicrorobotGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrorobotGatewayApplication.class, args);
	}

}
