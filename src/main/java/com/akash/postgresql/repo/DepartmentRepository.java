package com.akash.postgresql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.postgresql.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
