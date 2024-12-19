package com.github.alexnascimento2121.mscartoes;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@Slf4j
public class MsCartoesApplication {

	public static void main(String[] args) {
		log.info("Informação: {}", "teste info");
		log.error("Erro: {}", "teste erro");
		log.warn("Aviso: {}", "teste warn");
		SpringApplication.run(MsCartoesApplication.class, args);
	}

}
