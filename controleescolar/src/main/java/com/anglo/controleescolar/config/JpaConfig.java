package com.anglo.controleescolar.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.anglo.controleescolar.repository.entity");
        return entityManagerFactoryBean;
    }
	
	@Bean
    public JpaVendorAdapter jpaVendorAdapter(Properties dataSourceProperties){
		boolean showSql = dataSourceProperties.getProperty("database.show.sql") == null ? false : Boolean.parseBoolean(dataSourceProperties.getProperty("database.show.sql"));
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(showSql);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return adapter;
    }
	
	@Bean(name = "dataSourceProperties")		
	public Properties dataSourceProperties(ServletContext servletContext){
		Properties properties = new Properties();
		String applicationPath = servletContext.getRealPath("");
		File file = new File(applicationPath + "/WEB-INF/classes/database.properties");
		if (file.exists()) {
			try {				
				InputStream inputStream = new FileInputStream(file);
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}else {			
			properties.put("database.driver", "com.mysql.jdbc.Driver");
			properties.put("database.url", "jdbc:mysql://localhost:3306/controleescolar?useUnicode=true&characterEncoding=UTF-8&useTimezone=true&serverTimezone=Brazil/East");
			properties.put("database.username", "root");
			properties.put("database.password", "root");
			properties.put("database.show.sql", "false");
			try {
				OutputStream outputStream = new FileOutputStream(file);
				properties.store(outputStream, "Database properties");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}			
		}
		return properties;
	}
	
	@Bean
	public DataSource dataSource(Properties dataSourceProperties){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceProperties.getProperty("database.driver"));
		dataSource.setUrl(dataSourceProperties.getProperty("database.url"));
		dataSource.setUsername(dataSourceProperties.getProperty("database.username"));
		dataSource.setPassword(dataSourceProperties.getProperty("database.password"));
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
}