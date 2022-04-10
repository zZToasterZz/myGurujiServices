package com.srdt.myguruji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
//@EnableScheduling
public class MyGurujiApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(MyGurujiApplication.class, args);
	}
}
