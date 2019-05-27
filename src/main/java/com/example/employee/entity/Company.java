package com.example.employee.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue
    private Integer id;

    private String companyName;

    private Integer employeesNumber;
}
