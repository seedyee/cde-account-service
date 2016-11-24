package io.cde.account.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 * @createDate 2016年11月18日下午4:33:49
 *
 */
@Component
public class ErrorMessageSourceHandler {
	
	@Autowired
	private  ResourceBundleMessageSource messageSource;
	
	/**
	 * 根据
	 * @param messageKey
	 * @return
	 */
	public String getMessage(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		return message;
	}
}
