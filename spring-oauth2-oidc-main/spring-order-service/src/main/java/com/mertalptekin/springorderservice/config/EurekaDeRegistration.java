package com.mertalptekin.springorderservice.config;

import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class EurekaDeRegistration {

    private final EurekaClient eurekaClient;

    public EurekaDeRegistration(EurekaClient eurekaClient){
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
