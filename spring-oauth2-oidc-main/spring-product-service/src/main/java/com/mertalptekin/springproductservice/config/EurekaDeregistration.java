package com.mertalptekin.springproductservice.config;

import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;


@Component
public class EurekaDeregistration {

    private final EurekaClient eurekaClient;

    public EurekaDeregistration(EurekaClient eurekaClient){
        this.eurekaClient = eurekaClient;
    }

    @PreDestroy
    public void unregister(){
        // Uygulama durduğunda GC bunu temizlerken bağlantıyı manuel kes
        // En garanti yol.
        // Bağlantıyı kes.
        this.eurekaClient.shutdown();
    }
}