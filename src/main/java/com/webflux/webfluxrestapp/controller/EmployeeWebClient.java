package com.webflux.webfluxrestapp.controller;

import com.webflux.webfluxrestapp.model.Employee;
import com.webflux.webfluxrestapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class EmployeeWebClient {
    private final WebClient client;
    private final EmployeeService service;

    @Autowired
    public EmployeeWebClient(WebClient.Builder webClientBuilder, EmployeeService service) {
        this.client = webClientBuilder.baseUrl("http://localhost:8080/employees_client").build();
        this.service = service;
    }

    Mono<Employee> getEmployeeById(String id) {
        return client.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Employee.class);
    }

    Flux<Employee> getAllEmployees() {
        return client.get()
                //.uri("/")
                .retrieve()
                .bodyToFlux(Employee.class);
    }
}
