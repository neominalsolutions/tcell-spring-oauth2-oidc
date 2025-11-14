package com.mertalptekin.springorderservice.presentation.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;

@Component
public class FeignTracingInterceptor implements RequestInterceptor {

    private final Tracer tracer;

    public FeignTracingInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {

        System.out.println("Feign Product Service Client Interceptor");

        var currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            requestTemplate.header("b3", currentSpan.context().traceId() + "-" + currentSpan.context().spanId() + "-1");
        }
    }
}