package com.anglo.controleescolar.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
    protected Class<?>[] getRootConfigClasses() {
		return new Class[] { JpaConfig.class, BasicConfig.class, SecurityConfig.class };
    }

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {MvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
