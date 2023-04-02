package com.webflux.webfluxrestapp.model;

import lombok.*;

@Data
@Builder
public class Employee {
    private int id;
    private String name, jobTitle;
    private int yearsOfExperience;
}
