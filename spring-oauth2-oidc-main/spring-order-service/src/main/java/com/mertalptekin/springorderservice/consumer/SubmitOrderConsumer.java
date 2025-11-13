package com.mertalptekin.springorderservice.consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SubmitOrderConsumer {

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
