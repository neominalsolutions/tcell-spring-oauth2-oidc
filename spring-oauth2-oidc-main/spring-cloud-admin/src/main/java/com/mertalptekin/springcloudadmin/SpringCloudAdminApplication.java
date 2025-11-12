package com.mertalptekin.springcloudadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 1. @EnableAdminServer ile bunun Spring Cloud Admin Projesi olduğunu söylemek
// 2. spring.security.user.name=admin, spring.security.user.password=P@ssword1 -> Admin kullanıcı ayarları
// 3. Security Config ayarı


@SpringBootApplication
@EnableAdminServer
public class SpringCloudAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAdminApplication.class, args);
	}

}
