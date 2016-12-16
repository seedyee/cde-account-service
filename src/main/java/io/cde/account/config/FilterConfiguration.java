package io.cde.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cde.account.tools.MyFillter;

/**
 * @author lcl
 *
 */
@Configuration
public class FilterConfiguration {
	
	private MyFillter myFilter;
	
	/**
	 * 
	 */
	@Autowired
	public FilterConfiguration(MyFillter myFilter) {
		this.myFilter = myFilter;
	}
	
	/**
	 * 注入MyFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean init(){
		 return new FilterRegistrationBean(this.myFilter);
	}
}
