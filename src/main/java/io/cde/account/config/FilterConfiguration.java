package io.cde.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cde.account.tools.RequestFilter;

/**
 * @author lcl
 *
 */
@Configuration
public class FilterConfiguration {
    /**
     * myFilter.
     */
    private RequestFilter requestFilter;

    /**
     * 使用构造方法实例化myFilter.
     *
     * @param requestFilter myFilter对象
     */
    @Autowired
    public FilterConfiguration(final RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    /**
     * 注入MyFilter.
     *
     * @return 返回FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean init() {
        return new FilterRegistrationBean(this.requestFilter);
    }
}
