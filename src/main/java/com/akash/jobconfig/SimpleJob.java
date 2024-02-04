package com.akash.jobconfig;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.akash.listener.SkipListenerImpl;
import com.akash.postgresql.entities.Student;
import com.akash.processor.DbMigrationItemProcessor;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class SimpleJob {

	@Autowired
	private SkipListenerImpl skipListenerImpl;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	@Qualifier("universityDatasource")
	private DataSource universityDataSource;

	@Autowired
	@Qualifier("postgresDatasource")
	private DataSource postgresDataSource;

	@Autowired
	@Qualifier("postgresqlEntityManagerFactory")
	private EntityManagerFactory postgresEntityManagerFactory;

	@Autowired
	@Qualifier("mysqlEntityManagerFactory")
	private EntityManagerFactory mysqlEntityManagerFactory;

	@Autowired
	private DbMigrationItemProcessor dbMigrationItemProcessor;


	@Autowired
	@Qualifier("transactionManager")
	private JpaTransactionManager jpaTransactionManager;

	@Bean
	Job chunkJob(JobRepository jobRepository, Step chunkStep) {
		System.out.println("Job started.....");
		return new JobBuilder("Chunk Job", jobRepository).incrementer(new RunIdIncrementer()).start(chunkStep).build();
	}

	@Bean
	Step chunkStep(JobRepository jobRepository, ItemReader<Student> reader,
			ItemWriter<com.akash.mysql.entities.Student> writer) {
		System.out.println("Step1 started.....");
		return new StepBuilder("steps", jobRepository).<Student, com.akash.mysql.entities.Student>chunk(1000,
				jpaTransactionManager).reader(reader).processor(dbMigrationItemProcessor).writer(writer).faultTolerant()
				.skip(Throwable.class).skipLimit(10).retryLimit(3).retry(Throwable.class).listener(skipListenerImpl)
				.build();
	}


	@Bean
	JpaCursorItemReader<Student> jpaCursorItemReader() {
		JpaCursorItemReader<Student> jpaCursorItemReader = new JpaCursorItemReader<>();
		System.out.println("Inside Reader1");
		jpaCursorItemReader.setEntityManagerFactory(postgresEntityManagerFactory);
		jpaCursorItemReader.setQueryString("From Student");

		System.out.println(jpaCursorItemReader.getCurrentItemCount());

		return jpaCursorItemReader;
	}

	@Bean
	JpaItemWriter<com.akash.mysql.entities.Student> jpaItemWriter() {
		System.out.println("Inside Writer1");
		JpaItemWriter<com.akash.mysql.entities.Student> jpaItemWriter = new JpaItemWriter<>();
		jpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);

		return jpaItemWriter;

	}



}
