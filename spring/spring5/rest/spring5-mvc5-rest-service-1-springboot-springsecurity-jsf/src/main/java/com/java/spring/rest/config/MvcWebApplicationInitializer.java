package com.java.spring.rest.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// Load database and spring security configuration
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class, WebSecurityConfig.class };
	}

	// Load spring web configuration
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
