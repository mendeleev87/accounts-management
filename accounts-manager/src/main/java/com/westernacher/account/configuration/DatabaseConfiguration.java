package com.westernacher.account.configuration;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@EnableJpaRepositories("com.westernacher.account.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Value("${spring.datasource.driverClassName}")
	private String databaseDriverClassName;

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Value("${spring.datasource.username}")
	private String databaseUsername;

	@Value("${spring.datasource.password}")
	private String databasePassword;

	@Bean
	public DataSource dataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setPassword(databasePassword);
		ds.setUser(databaseUsername);
		ds.setUrl(dataSourceUrl);
		return ds;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource());
		lef.setJpaVendorAdapter(jpaVendorAdapter());
		lef.setPackagesToScan("com.westernacher.account.*");
		Properties jpaProperties = new Properties();
		jpaProperties.put("eclipselink.weaving", "false");
		jpaProperties.put("javax.persistence.validation.mode", "AUTO");

		lef.setJpaProperties(jpaProperties);
		lef.afterPropertiesSet();
		return lef.getObject();
	}

	@Bean
	public EntityManager enitityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();

		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setGenerateDdl(false);

		return jpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return Validation.buildDefaultValidatorFactory();
	}
	
	@Bean
	public Validator validator() {
		return validatorFactory().getValidator();
	}
	
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
