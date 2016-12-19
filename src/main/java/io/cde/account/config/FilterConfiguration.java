package io.cde.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cde.account.tools.MyRequestFilter;

/**
 * @author lcl
 *
 */
@Configuration
public class FilterConfiguration {
    /**
     * myFilter.
     */
    private MyRequestFilter myRequestFilter;

    /**
     * 使用构造方法实例化myRequestFilter.
     *
     * @param myRequestFilter myRequestFilter对象
     */
    @Autowired
    public FilterConfiguration(@Qualifier(value = "myRequestFilter") final MyRequestFilter myRequestFilter) {
        this.myRequestFilter = myRequestFilter;
    }

    /**
     * 注入MyFilter.
     *
     * @return 返回FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean init() {
        return new FilterRegistrationBean(this.myRequestFilter);
    }
}
