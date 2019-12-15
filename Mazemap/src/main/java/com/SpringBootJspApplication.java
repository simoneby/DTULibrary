package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//@Author s154666
@SpringBootApplication
public class SpringBootJspApplication<User> extends SpringBootServletInitializer 
{

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
   {
      return application.sources(SpringBootJspApplication.class);
   }
   public static void main(String[] args) {

      SpringApplication.run(SpringBootJspApplication.class, args);

   }
}