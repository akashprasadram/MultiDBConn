package com.akash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.postgresql.entities.Department;
import com.akash.postgresql.repo.DepartmentRepository;

@RestController
@RequestMapping("/api/v1")
public class PGController {

	@Autowired
	DepartmentRepository repository;

	@GetMapping("/pgsql/getDepartment/")
	public List<Department> getDepartment() {
		System.out.println("Inside PSGL getDepartment");
		return repository.findAll();
	}

}
