package com.webflux.webfluxrestapp.controller;

import com.webflux.webfluxrestapp.model.Employee;
import com.webflux.webfluxrestapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeControllerWithReactiveJava {
    private final EmployeeService service;

    @GetMapping
    Flux<Employee> getAll() {
        return Flux.fromIterable(service.list())
                .onErrorResume(error -> {
                    log.error("Error retrieving employees: {}", error.getMessage());
                    return Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving employees"));
                });
    }

    @GetMapping("/{id}")
    Mono<Employee> getById(@PathVariable Integer id) {
        return Mono.justOrEmpty(service.getById(id))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")))
                .cast(Employee.class);
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Object>> deleteById(@PathVariable Integer id) {
        return Mono.just(service.deleteById(id))
                .map(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build())
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PostMapping
    Mono<ResponseEntity<Object>> save(@RequestBody Employee employee) {
        return Mono.just(service.add(employee))
                .map(created -> created ? ResponseEntity.created(URI.create("/employees/" + employee.getId())).build() : ResponseEntity.badRequest().build())
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<Object>> updateById(@PathVariable Integer id, @RequestBody Employee employee) {
        return Mono.just(service.update(id, employee))
                .map(updated -> updated ? ResponseEntity.created(URI.create("/employees/" + employee.getId())).build() : ResponseEntity.badRequest().build())
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
}
