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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Login sayfasında authenticate olabilmek için bunu açtık.
        // sağlık verilerine sadece Admin client olanlar erişebilsin
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/assets/**","/login").permitAll().requestMatchers("/intances","/actuator/**").hasRole("ADMIN_CLIENT").anyRequest().authenticated()).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

        return http.build();
    }



    // InMemory User Details Manager
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("admin").password("{noop}P@ssword1").roles("ADMIN_CLIENT").build();
        return new InMemoryUserDetailsManager(user);
    }

}
