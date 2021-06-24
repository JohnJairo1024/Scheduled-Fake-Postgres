package com.sample.postgress.service;

import com.sample.postgress.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    void insertEmployee(Employee emp);

    void updateEmployee(Employee emp);

    void executeUpdateEmployee(Employee emp);

    void deleteEmployee(Employee emp);

}
