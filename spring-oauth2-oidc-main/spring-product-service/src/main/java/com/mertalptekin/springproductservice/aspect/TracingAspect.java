package com.mertalptekin.springproductservice.aspect;


import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

    private final Tracer tracer;

    public TracingAspect(Tracer tracer) {
        this.tracer = tracer;
    }

    // execution(* package..*(..)) -> package altındaki tüm methodlar
    @Around("execution(* com.mertalptekin.springproductservice..*(..))")
    public Object traceServiceMethods(ProceedingJoinPoint pjp) throws Throwable {


        System.out.println("TracingAspect.traceServiceMethods");

        // Mevcut span üzerinden child span aç
        Span span = tracer.nextSpan().name(pjp.getSignature().toShortString()).start();
        try {
            // Parametreleri tag olarak ekle
            Object[] args = pjp.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    span.tag("arg" + i, args[i].toString());
                }
            }

            // Child span aktifken method çalışır
            return pjp.proceed();
        } finally {
            span.end();
        }
    }
}