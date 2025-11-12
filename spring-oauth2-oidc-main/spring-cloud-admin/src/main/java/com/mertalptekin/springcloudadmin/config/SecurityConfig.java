package com.mertalptekin.springcloudadmin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // form login üzerinden basic username password auth yapılacak.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Login sayfasında authenticate olabilmek için bunu açtık.
        // sağlık verilerine sadece Admin client olanlar erişebilsin
        // Spring Boot Admin Client, Admin Server’a POST /instances isteği atar.
        //Bu isteğin gövdesinde kendi bilgisini (url, metadata vs.) gönderir form üzerinden gönderim yaparke csrf ek olarak Request Verification Token bekler bu sebeple POST isteği başarısız olur. Bunu eklememiz gerekir.
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/assets/**","/login").permitAll().requestMatchers("/instances","/actuator/**").permitAll().anyRequest().authenticated()).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
