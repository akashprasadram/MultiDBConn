package com.akash.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.mysql.entities.Student;

@Repository("mysqlStudentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {

}
