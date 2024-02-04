package com.akash.config;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration

@EnableJpaRepositories(basePackages = { "com.akash.mysql.repo" }
, entityManagerFactoryRef = "mysqlEntityManagerFactory"
,transactionManagerRef ="transactionManager")
@EntityScan(basePackages = {"com.akash.mysql.entities"})
public class MySqlDatabaseConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.universitydatasource")
	DataSource universityDatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mysqlEntityManagerFactory")
	EntityManagerFactory mysqlEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(universityDatasource());
		lem.setPackagesToScan("com.akash.mysql.entities");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.afterPropertiesSet();

		return lem.getObject();
	}

	@Bean(name = "transactionManager")
	@Primary
	JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(mysqlEntityManagerFactory());
	}

}
