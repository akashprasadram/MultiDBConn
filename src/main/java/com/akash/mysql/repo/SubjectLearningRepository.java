package com.akash.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.mysql.entities.SubjectsLearning;

@Repository("mysqlSubjectRepository")
public interface SubjectLearningRepository extends JpaRepository<SubjectsLearning, Long> {

}
