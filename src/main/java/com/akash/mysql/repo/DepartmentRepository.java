package com.akash.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.mysql.entities.Department;

@Repository("mysqlDepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
