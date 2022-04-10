package com.srdt.myguruji.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	private static final String SWAGGER_API_VERSION = "6.0";
	private static final String LICENSE_TEST = "License";
	private static final String title = "'MyGuruJi Endpoint(REST API)";
	private static final String description = "Endpoint All";

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description(description).license(LICENSE_TEST)
				.version(SWAGGER_API_VERSION).build();
	}

	@Bean
	public Docket LocationApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).pathMapping("/").select().paths(regex("/api.*"))
				.build();
	}
}
