package com.akash.config;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = { "com.akash.postgresql.repo" }
, entityManagerFactoryRef = "postgresqlEntityManagerFactory"
,transactionManagerRef="postgresqlTransactionManager")
@EntityScan(basePackages = {"com.akash.postgresql.entities"})
public class PostgresSqlDatabaseConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.postgresdatasource")
	DataSource postgresDatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "postgresqlEntityManagerFactory")
	EntityManagerFactory postgresqlEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(postgresDatasource());
		lem.setPackagesToScan("com.akash.postgresql.entities");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.afterPropertiesSet();

		return lem.getObject();
	}

	@Bean(name = "postgresqlTransactionManager")
	PlatformTransactionManager postgresqlTransactionManager(
			@Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
		return new JpaTransactionManager(postgresqlEntityManagerFactory());
	}

}
