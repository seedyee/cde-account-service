package io.cde.account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import io.cde.account.tools.MyFillter;

/**
 * 程序入口.
 * 
 * @author lcl
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Application.class);
		logger.info("service start");
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public static FilterRegistrationBean init(){
		 return new FilterRegistrationBean(new MyFillter());
	}
}