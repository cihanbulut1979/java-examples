package com.java.spring.rest.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.java" })
public class WebMvcConfig implements WebMvcConfigurer {

	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		 //registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    	 //registry.jsp().prefix("/WEB-INF/views/faces/").suffix(".xhtml");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	 //registry.addViewController("/login").setViewName("login");
    	 //registry.addViewController("/").setViewName("home/home");
         //registry.addViewController("/home").setViewName("home/home");
         //registry.addViewController("/logoutSuccessful").setViewName("login");
         //login URL inden gelen bilgisiyi loginPage e yönlendir. URL ama login kalacak
         
    }
    
   
}