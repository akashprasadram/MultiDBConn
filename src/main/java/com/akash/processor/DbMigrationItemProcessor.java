package com.akash.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.akash.postgresql.entities.Student;

@Component
public class DbMigrationItemProcessor implements ItemProcessor<Student, com.akash.mysql.entities.Student> {

	@Override
	public com.akash.mysql.entities.Student process(Student item) throws Exception {

		System.out.println("Processor ="+item.toString());
		com.akash.mysql.entities.Student student = new com.akash.mysql.entities.Student();
		student.setId(item.getId());
		student.setFirstName(item.getFirstName());
		student.setLastName(item.getLastName());
		student.setEmail(item.getEmail());
		student.setDeptId(item.getDeptId());
		student.setIsActive(item.getIsActive() != null ? Boolean.valueOf(item.getIsActive()) : false);
		System.out.println("Mysql: "+student.toString());
		return student;
	}

}
