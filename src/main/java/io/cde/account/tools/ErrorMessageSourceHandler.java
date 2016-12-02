package io.cde.account.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 * 
 */
@Component
public class ErrorMessageSourceHandler {

	@Autowired
	private  ResourceBundleMessageSource messageSource;

    /**
	 * 根据errorKey从配置文件中获取相信的信息.
	 * 
	 * @param messageKey 错误信息在配置文件中存储的key值
	 * @return 返回获取到的value
	 */
    public String getMessage(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		return message;
	}
}
