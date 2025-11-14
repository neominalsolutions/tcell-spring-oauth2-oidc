package com.mertalptekin.springorderservice.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springorderservice.events.OrderSubmittedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SubmitOrderConsumer {

    private final ObjectMapper objectMapper;

    public SubmitOrderConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Bean
    public Consumer<Message<String>> stockOut(){
        return message -> {
            String payload = message.getPayload();
            try {
                var event = objectMapper.readValue(payload, OrderSubmittedEvent.class);
                var Headers = message.getHeaders();
                System.out.println("event-order-code" + event.orderCode());
                System.out.println("stockOut "  + payload);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<Message<String>> doShipment(){
        return message -> {
            String payload = message.getPayload();
            var Headers = message.getHeaders();

            System.out.println("doShipment "  + payload);

            // consume ederken mesajda bir hata olması durumunda DLQ ile mesajı yedekliyoruz. Farklı bir topic taşıyoruz.
            if(payload.contains("fail")){
                throw new RuntimeException("Shipping Process Error");
            }


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
