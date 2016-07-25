package com.westernacher.account.configuration.test;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.westernacher.account.service.AccountService;
import com.westernacher.account.service.SpringDataAccountService;

@Configuration
public class TestConfiguration {

//	@Bean
//	public AccountService getAccountService() {
//		return new SpringDataAccountService();
//	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:accounts.sql")
				.addScript("classpath:sample_data.sql").build();
		return db;
	}

}
