package com;
import com.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class SpringBootJspApplication extends SpringBootServletInitializer 
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