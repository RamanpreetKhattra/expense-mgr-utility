package com.ionwallet.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ionwallet.filters.AuthFilter;

@Configuration
public class AuthFilterConfiguration {

	@Bean
	public FilterRegistrationBean authFilterRegistration(){
		FilterRegistrationBean registration =new FilterRegistrationBean();
		registration.setFilter(new AuthFilter());
		registration.addUrlPatterns("/users/*");
		registration.setOrder(1);
		return registration;
	}
}
