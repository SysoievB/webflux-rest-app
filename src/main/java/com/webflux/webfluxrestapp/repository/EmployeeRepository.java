package com.webflux.webfluxrestapp.repository;

import com.webflux.webfluxrestapp.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees;

    public EmployeeRepository() {
        this.employees = List.of(
                Employee.builder().id(1).name("John").jobTitle("Project manager").yearsOfExperience(3).build(),
                Employee.builder().id(2).name("Jack").jobTitle("Developer").yearsOfExperience(10).build(),
                Employee.builder().id(3).name("John").jobTitle("PR marketing").yearsOfExperience(5).build()
        );
    }

    public Optional<Employee> getEmployeeById(Integer id) {
        return Optional.of(employees.get(id));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public boolean deleteEmployeeById(Integer id) {
        return employees.removeIf(employee -> employee.getId() == id);
    }

    public boolean addNewEmployee(Employee employee) {
        return employees.add(employee);
    }
}
