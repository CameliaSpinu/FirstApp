package com.kspinu.repository;

import com.kspinu.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Employee findById(Employee employee, long id);
}

