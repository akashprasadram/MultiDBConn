package com.akash.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.akash.postgresql.entities.Department;
import com.akash.postgresql.entities.Student;
import com.akash.postgresql.entities.SubjectsLearning;

@Component
public class DbMigrationItemProcessor1
		implements ItemProcessor<SubjectsLearning, com.akash.mysql.entities.SubjectsLearning> {

	@Override
	public com.akash.mysql.entities.SubjectsLearning process(SubjectsLearning item) throws Exception {

		System.out.println("Subject Processor =" + item.toString());
		com.akash.mysql.entities.SubjectsLearning subjectsLearning = new com.akash.mysql.entities.SubjectsLearning();
		subjectsLearning.setId(item.getId());
		subjectsLearning.setSubName(item.getSubName());
		subjectsLearning.setMarksObtained(item.getMarksObtained());
		subjectsLearning.setStudentId(item.getStudentId());

		System.out.println("Subject Mysql: " + subjectsLearning.toString());
		return subjectsLearning;
	}

}
