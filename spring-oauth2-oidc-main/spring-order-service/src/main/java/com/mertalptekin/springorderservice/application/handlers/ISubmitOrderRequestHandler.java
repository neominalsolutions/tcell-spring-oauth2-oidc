package com.mertalptekin.springorderservice.application.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mertalptekin.springorderservice.application.requests.SubmitOrderRequest;
import com.mertalptekin.springorderservice.application.responses.SubmitOrderResponse;

public interface ISubmitOrderRequestHandler {

    SubmitOrderResponse handle(SubmitOrderRequest req) throws JsonProcessingException;

}
