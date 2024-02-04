package com.akash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.mysql.entities.Department;
import com.akash.mysql.repo.DepartmentRepository;

@RestController
@RequestMapping("/api/v1")
public class MySqlController {

	@Autowired
	@Qualifier("mysqlDepartmentRepository")
	DepartmentRepository repository;

	@GetMapping("/mysql/getDepartment/")
	public List<Department> getDepartment() {
		System.out.println("Inside MYSQL Department");
		return repository.findAll();
	}

}
