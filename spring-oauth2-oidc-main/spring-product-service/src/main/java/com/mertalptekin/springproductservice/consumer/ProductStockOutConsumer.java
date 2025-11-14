package com.mertalptekin.springproductservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springproductservice.events.OrderSubmittedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProductStockOutConsumer {
    private final ObjectMapper objectMapper;

    public ProductStockOutConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }


    @Bean
    public Consumer<Message<String>> stockOut(){
        return message -> {

            try {
               OrderSubmittedEvent event=  objectMapper.readValue(message.getPayload(), OrderSubmittedEvent.class);
               System.out.println("Product Repository or Inventory Service  Update Stock" + event);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        };
    }
}
