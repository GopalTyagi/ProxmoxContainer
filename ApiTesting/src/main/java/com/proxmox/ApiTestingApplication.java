package com.proxmox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.proxmox"})
public class ApiTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTestingApplication.class, args);
	}

}