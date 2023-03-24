package com.example.stageapp;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication //Configuration + EnableAutoConfiguration + ComponentScan
@Log
@EnableFeignClients
@EnableDiscoveryClient
public class StageAppApplication{

	public static void main(String[] args) {
		SpringApplication.run(StageAppApplication.class, args);
	}



}
