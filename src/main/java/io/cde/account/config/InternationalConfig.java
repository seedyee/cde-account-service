package io.cde.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author lcl
 * @createDate 2016年11月18日下午4:21:25
 * 国际化messageSource 配置
 */
@Configuration
public class InternationalConfig {
	
	@Value(value = "${spring.messages.basename}")
	private String basename;
	
	@Bean
	public ResourceBundleMessageSource getMessageResource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(basename);
		return messageSource;
	}
}
