package io.cde.account.tools;

import java.util.Locale;

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

	private Locale locale;
	
	private ErrorMessageSourceHandler(Locale locale) {
		this.locale = locale;
	}
    /**
	 * 根据errorKey从配置文件中获取相信的信息
	 * @param messageKey 错误信息在配置文件中存储的key值
	 * @return 返回获取到的value
	 */
    public String getMessage(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		return message;
	}
	
	/**
	 * 根据当前设置的Locale获取资源信息
	 * @param messageKey
	 * @return
	 */
	public String getMessageByLocale(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, this.locale);
		return message;
	}
}
