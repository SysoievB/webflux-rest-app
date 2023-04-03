package com.webflux.webfluxrestapp.service;

import com.webflux.webfluxrestapp.model.Employee;
import com.webflux.webfluxrestapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> list() {
        return repository.getAllEmployees();
    }

    public Optional<Employee> getById(Integer id) {
        return repository.getEmployeeById(id);
    }

    public boolean deleteById(Integer id) {
        return repository.deleteEmployeeById(id);
    }

    public boolean add(Employee employee) {
        return repository.addNewEmployee(employee);
    }

    public boolean update(Integer id, Employee employee) {
        return repository.updateEmployeeById(id, employee);
    }
}
