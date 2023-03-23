package com.example.stageapp;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication //Configuration + EnableAutoConfiguration + ComponentScan
@Log
@EnableFeignClients
@EnableDiscoveryClient
public class StageAppApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StageAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
