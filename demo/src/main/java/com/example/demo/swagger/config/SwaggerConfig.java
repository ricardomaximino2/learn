package com.example.demo.swagger.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	private final String title ="Title";
	private final String description = "Descripition";
	private final String version = "0.1.0";
	private final String termsOfServiceUrl = "TermsOfServiceUrl";
	private final String author = "BrasaJava";
	private final String webAuthor ="http://www.brasajava.com";
	private final String email = "Contact";
	private final String license = "License text";
	private final String licenseUrl = "http://www.license.url.com";
	
	@Bean
	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo"))
		        .paths(PathSelectors.	regex("/rest.*"))
		        .build()
		        .apiInfo(getApiInfo())
		        .pathMapping("/");        
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.version(version)
				.termsOfServiceUrl(termsOfServiceUrl)
				.contact(new Contact(author, webAuthor, email))
				.license(license)
				.licenseUrl(licenseUrl).build();
	}	
}
