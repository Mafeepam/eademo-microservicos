package com.espacos_academicos.usuariosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Não é estritamente necessário @EnableDiscoveryClient ou @EnableEurekaClient
// se spring-cloud-starter-netflix-eureka-client estiver no classpath,
// mas pode ser explícito se preferir.
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// @EnableDiscoveryClient // Opcional
public class UsuariosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
    }
}