package io.cde.account.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 * @createDate 2016年11月18日下午4:33:49
 *
 */
@Component
public class ErrorMessageSourceHandler {
	
	@Autowired
	private  MessageSource messageSource;
	
	/**
	 * 根据属性名从资源文件中获取错误码
	 * @param messageKey
	 * @return 返回int类型的错误码
	 */
	public int getCode(String codeKey) {
		String messageCode = messageSource.getMessage(codeKey, null, LocaleContextHolder.getLocale());
		return Integer.parseInt(messageCode);
	}
	
	public String getMessage(String messageKey) {
		String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		return message;
	}
}
