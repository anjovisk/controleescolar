package com.anglo.controleescolar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.theme.SessionThemeResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.anglo.controleescolar.repository"})
@ComponentScan(basePackages = { "com.anglo.controleescolar.controller" })
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new GzipResourceResolver())
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean(name = "themeSource")
    public ResourceBundleThemeSource themeSource() {
    	ResourceBundleThemeSource source = new ResourceBundleThemeSource();
    	source.setBasenamePrefix("classpath:theme");
        return source;
    }
    
    @Bean(name = "themeResolver")
    public ThemeResolver themeResolver() {
    	SessionThemeResolver resolver = new SessionThemeResolver();
    	resolver.setDefaultThemeName("theme_default");
        return resolver;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
    	CommonsMultipartResolver result = new CommonsMultipartResolver();
    	result.setDefaultEncoding("utf-8");
    	return result;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }
}