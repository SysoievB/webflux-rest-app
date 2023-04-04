package com.webflux.webfluxrestapp.controller;

import com.webflux.webfluxrestapp.model.Employee;
import com.webflux.webfluxrestapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
@RequestMapping("/employees-webflux")
@AllArgsConstructor
public class EmployeeControllerWithSpringWebFlux {
    private final EmployeeService service;

    @GetMapping
    RouterFunction<ServerResponse> getEmployeeById() {
        return route(GET("/{id}"), req -> {
            String idStr = req.pathVariable("id");
            if (!idStr.matches("\\d+")) {
                return badRequest().bodyValue("Invalid ID: " + idStr);
            }
            int id = Integer.parseInt(idStr);
            return ok().body(service.getById(id), Employee.class);
        });
    }
}
