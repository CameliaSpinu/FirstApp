package com.kspinu.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.kspinu.model.Employee;
import com.kspinu.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @PostMapping()
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        Optional<Employee> employee1 = employeeRepository.findById(employeeId);

        if (employee1.isPresent()) {
            return new ResponseEntity<Employee>(employee1.get(), HttpStatus.OK);
        } else {
            throw new NoSuchElementException("Employee ID: " + employeeId + "  doesn't exist");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id
            , @RequestBody Employee employee) {

        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Employee ID: " + id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        employeeRepository.save(existingEmployee);
        return new ResponseEntity<>(existingEmployee, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {

        employeeRepository.deleteById(id);

        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);
    }
}
