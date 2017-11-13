package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
// to user as war
//public class ServletInitializer extends SpringBootServletInitializer{
public class ServletInitializer{
	
//	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

}
