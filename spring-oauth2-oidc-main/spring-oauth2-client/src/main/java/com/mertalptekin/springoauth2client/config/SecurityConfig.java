package com.mertalptekin.springoauth2client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Resource Owner projeleri JWT validation JWKS üzerinden Auth Serverdan kontrol edip gelen istekleri alıp almamk ile
    // ilgilenir

    // Burası client olduğu için ama AUTH Server'a login olup, TOKEn bilgileri alıp, Resource Server'a
    // HTTP Request göndermektir.

    // Spring MVC Client -> Resource Server (Spring MVC RestService) -> Auth Server Spring App

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // oauth2Login ile süreci AUTH Server devrediyoruz. Login sayfasına sahip değil. AUTH Server üzerinden login olarak yapıyor
        // Bütün AUTH süreci yine AUTH SERVER üzerinden işliyor.
        // React uygulamalarında oturum bilgiler Web Storageda tutulabiliyordu ama Spring MVC client uygulamalarında bu anlık
        // oturum bilgilerini tutumak için ya session kullanıcaz, yada güvenli http only cookie ile yöneteceğiz.

        http
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("api/config/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization ->
                                authorization
                                        .authorizationRequestRepository(new HttpCookieOAuth2AuthorizationRequestRepository())
                        )
                );
        return http.build();
    }

}
