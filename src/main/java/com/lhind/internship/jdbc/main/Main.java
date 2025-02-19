package com.lhind.internship.jdbc.main;


import com.lhind.internship.jdbc.model.Employee;
import com.lhind.internship.jdbc.repository.EmployeeRepository;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = new Employee();

        employee.setEmployeeNumber(1);
        employee.setFirstName("testing update Insert");
        employee.setLastName("test");
        employee.setExtension("");
        employee.setEmail("email");
        employee.setOfficeCode("1");
        employee.setReportsTo(1002);
        employee.setJobTitle("jobTitle");


        //System.out.println(employeeRepository.findAll());

        //System.out.println("Employee exists: " +  employeeRepository.exists(2));

        //employeeRepository.findById(1);

        //employeeRepository.delete(7);

        //employeeRepository.insertEmployee(employee);

        //employeeRepository.updateEmployee(employee);

        employeeRepository.save(employee);


    }
}
