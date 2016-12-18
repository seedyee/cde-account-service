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
    /**
     * myFilter.
     */
    private MyFillter myFilter;

    /**
     * 使用构造方法实例化myFilter.
     *
     * @param myFilter myFilter对象
     */
    @Autowired
    public FilterConfiguration(final MyFillter myFilter) {
        this.myFilter = myFilter;
    }

    /**
     * 注入MyFilter.
     *
     * @return 返回FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean init() {
        return new FilterRegistrationBean(this.myFilter);
    }
}
