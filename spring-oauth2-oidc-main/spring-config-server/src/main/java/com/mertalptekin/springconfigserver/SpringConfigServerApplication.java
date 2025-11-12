package com.mertalptekin.springconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

// uygulama config klasöründen ilgili config değerlerini okuyabilmesi için @EnableConfigServer anatasyonu ile config server aktif kale getirdik.

@SpringBootApplication
@EnableConfigServer
public class SpringConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigServerApplication.class, args);
	}

}
