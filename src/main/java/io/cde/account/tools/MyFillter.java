package io.cde.account.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.cde.account.config.FilterConfiguration;
import io.cde.account.service.AccountService;
import io.cde.account.service.impl.AccountServiceImpl;;

/**
 * @author lcl
 *
 */
@Component
@WebFilter(filterName = "myfilter", urlPatterns = "/accounts")
public class MyFillter implements Filter {
    
	/**
	 * 允许访问的域名.
	 */
	@Value(value = "${cde.account.filter.origin}")
	private String origin;
	
	/**
	 * 支持访问的方式.
	 */
	@Value(value = "${cde.account.filter.methods}")
	private String methods;
	
	/**
	 * 请求头可以包含的参数项.
	 */
	@Value(value = "${cde.account.filter.headers}")
	private String headers;
	
	/**
	 * 请求参数的格式.
	 */
	@Value(value = "${cde.account.filter.content-type}")
	private String contentType;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response2 = (HttpServletResponse) response;
		response2.setHeader("Access-Control-Allow-Origin", this.origin);
		response2.setHeader("Access-Control-Allow-Methods", this.methods);
		response2.setHeader("Access-Control-Allow-Headers", this.headers);
		response2.setContentType(this.contentType);
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
