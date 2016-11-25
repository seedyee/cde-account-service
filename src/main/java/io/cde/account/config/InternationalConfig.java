package io.cde.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author lcl
 * 国际化messageSource 配置
 */
@Configuration
public class InternationalConfig {

    /**
    * 获取国际化资源文件的basename.
    */
    @Value(value = "${account.i18n.messages.basename}")
    private String basename;

    /**
    * 配置messageSource实体.
    * @return 返回messagesource对象.
    */
    @Bean
    public ResourceBundleMessageSource getMessageResource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        return messageSource;
    }
}
