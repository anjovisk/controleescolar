package com.anglo.controleescolar.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value="classpath:application.properties", ignoreResourceNotFound=true)
public class ApplicationProperties {
	@Value( "${default.url:https://www.anglo.com.br/controleescolar}" )
	private String defaultUrl;	
	@Value("enterprise.name:Anglo")
	private String enterpriseName;
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	
	public String getEnterpriseName() {
		return this.enterpriseName;
	}
}
