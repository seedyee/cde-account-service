package io.cde.account.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 */
@Component
public class ErrorMessageSourceHandler {

    /**
     * 注入ResourceBundleMessageSource对象.
     */
    private ResourceBundleMessageSource messageSource;

    /**
     * 构造器.
     *
     * @param messageSource ResourceBundleMessageSource对象
     */
    @Autowired
    public ErrorMessageSourceHandler(final ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 根据errorKey从配置文件中获取相信的信息.
     *
     * @param messageKey 错误信息在配置文件中存储的key值
     * @return 返回获取到的value
     */
    public String getMessage(final String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
