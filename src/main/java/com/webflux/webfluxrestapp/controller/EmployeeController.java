package com.webflux.webfluxrestapp.controller;

import com.webflux.webfluxrestapp.model.Employee;
import com.webflux.webfluxrestapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping
    public Flux<Employee> getAll() {
        return Flux.fromIterable(service.list())
                .onErrorResume(error -> {
                    log.error("Error retrieving employees: {}", error.getMessage());
                    return Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving employees"));
                });
    }
}
