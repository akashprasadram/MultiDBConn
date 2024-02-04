package com.akash.config;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(dataSource());
		lem.setPackagesToScan("com.akash.entities");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		lem.afterPropertiesSet();

		return lem.getObject();
	}

}
