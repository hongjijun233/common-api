package com.lianghongji.config;

import com.lianghongji.manageapi.controller.v1.AccessFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author liang.hongji
 *
 */
@Configuration
public class WebConfig {

	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}

	@Bean
	public FilterRegistrationBean accessFilterRegisters() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(accessFilter());
		filterRegistrationBean.addUrlPatterns(
				"/v1/manageApi/manageUser/*");
		return filterRegistrationBean;
	}
}