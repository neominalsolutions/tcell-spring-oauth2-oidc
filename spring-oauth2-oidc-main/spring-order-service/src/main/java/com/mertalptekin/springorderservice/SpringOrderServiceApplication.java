package com.mertalptekin.springorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
@EnableFeignClients
public class SpringOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOrderServiceApplication.class, args);
	}


    @Bean
    public Consumer<Message<String>> stockOut(){
        return message -> {
            String payload = message.getPayload();
            var Headers = message.getHeaders();

            System.out.println("stockOut "  + payload);

        };
    }

    @Bean
    public Consumer<Message<String>> doShipment(){
        return message -> {
            String payload = message.getPayload();
            var Headers = message.getHeaders();

            System.out.println("doShipment "  + payload);

        };
    }

    @Bean
    public Consumer<Message<String>> doPayment(){
        return message -> {
            String payload = message.getPayload();
            var Headers = message.getHeaders();

            System.out.println("doPayment "  + payload);

        };
    }

}
