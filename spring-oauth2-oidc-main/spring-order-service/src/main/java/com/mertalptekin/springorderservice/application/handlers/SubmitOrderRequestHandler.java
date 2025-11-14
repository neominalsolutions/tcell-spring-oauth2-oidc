package com.mertalptekin.springorderservice.application.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertalptekin.springorderservice.application.events.OrderSubmittedEvent;
import com.mertalptekin.springorderservice.application.requests.SubmitOrderRequest;
import com.mertalptekin.springorderservice.application.responses.SubmitOrderResponse;
import com.mertalptekin.springorderservice.domain.entity.Order;
import com.mertalptekin.springorderservice.domain.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SubmitOrderRequestHandler implements ISubmitOrderRequestHandler{

    private final StreamBridge streamBridge;
    private final IOrderService orderService;
    private final ObjectMapper objectMapper;

    public SubmitOrderRequestHandler(StreamBridge streamBridge, IOrderService orderService, ObjectMapper objectMapper){
        this.streamBridge = streamBridge;
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @Override
    public SubmitOrderResponse handle(SubmitOrderRequest req) throws JsonProcessingException {

        var entity = new Order();
        BeanUtils.copyProperties(req,entity);
        entity.setStatus("submitted");
        orderService.save(entity);

        // Inventory Service Send Integration Event ->
        var event = new OrderSubmittedEvent(req.code(),"submitted",req.quantity(),req.productName());
        var payload = objectMapper.writeValueAsString(event);
        // status fail olarak iletilirse
        Message<String> message = MessageBuilder.withPayload(payload).setHeader("eventType","orderSubmitted").build();
        // Kafkaya mesajımızı iletiyoruz
        boolean isSend =  streamBridge.send("orderSubmit-out-0",message);

        if(!isSend){
            throw  new RuntimeException("Order Submitted Failed");
        }

        System.out.println("Order Submitted Request is Completed");
        return new SubmitOrderResponse(isSend ? "Başarılı":"Başarısız");
    }
}
