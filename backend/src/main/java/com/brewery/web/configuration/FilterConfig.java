package com.brewery.web.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthHook> loggingFilter() {
        FilterRegistrationBean<AuthHook> registrationBean = new FilterRegistrationBean<AuthHook>();

        registrationBean.setFilter(new AuthHook());
        registrationBean.addUrlPatterns("/*");


        return registrationBean;
    }
}
