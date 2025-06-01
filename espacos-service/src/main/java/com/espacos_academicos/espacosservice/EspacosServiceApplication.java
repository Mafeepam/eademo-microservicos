package com.espacos_academicos.espacosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Ou @EnableEurekaClient, dependendo da sua versão do Spring Cloud
public class EspacosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EspacosServiceApplication.class, args);
    }

}