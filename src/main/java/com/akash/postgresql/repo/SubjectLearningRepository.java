package com.akash.postgresql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.postgresql.entities.SubjectsLearning;


@Repository
public interface SubjectLearningRepository extends JpaRepository<SubjectsLearning, Long> {

}
