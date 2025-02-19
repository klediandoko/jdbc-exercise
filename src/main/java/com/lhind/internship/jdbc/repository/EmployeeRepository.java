package com.lhind.internship.jdbc.repository;

import com.lhind.internship.jdbc.mapper.EmployeeMapper;
import com.lhind.internship.jdbc.model.Employee;
import com.lhind.internship.jdbc.model.enums.EmployeeQuery;
import com.lhind.internship.jdbc.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements Repository<Employee, Integer> {


    private EmployeeMapper employeeMapper = EmployeeMapper.getInstance();

    @Override
    public List<Employee> findAll() {
        final List<Employee> response = new ArrayList<>();
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.SELECT_ALL.getQuery())) {
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                response.add(employeeMapper.toEntity(result));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return response;
    }

    @Override
    public Optional<Employee> findById(final Integer id) {
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.SELECT_BY_ID.getQuery())) {
            statement.setInt(1, id);
            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final Employee employee = employeeMapper.toEntity(result);
                System.out.println(employee);
                return Optional.of(employee);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("No data found");
        return Optional.empty();
    }

    @Override
    public boolean exists(final Integer id) {
        try (final Connection connection = JdbcConnection.connect();
             final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.EMPLOYEE_EXISTS.getQuery()))
        {
            statement.setInt(1, id);
            final ResultSet result = statement.executeQuery();
            if (result.next() && result.getInt(1) == 1) {
               System.out.println("Employee with id: " + id + " does exist in the db!");
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Employee with specified id does not exist.");
        return false;
    }

    @Override
    public Employee save(final Employee employee) {
        Optional<Employee> savedEmployee;
        if (exists(employee.getEmployeeNumber())) {
            System.out.println("Id exists... Going to update employee info.");
            savedEmployee= updateEmployee(employee);
        } else {
            System.out.println("ID does not exist, saving new employee");
            savedEmployee= insertEmployee(employee);
        }
        return savedEmployee.orElseThrow(() -> new RuntimeException("Save Not Executed"));
    }

    @Override
    public void delete(final Integer id) {
        if (!exists(id)) {
            System.out.println("Employee with given id does not exist in db");
        } else {
            try {final Connection connection = JdbcConnection.connect();
                 final PreparedStatement deleteStatement = connection.prepareStatement(EmployeeQuery.DELETE_EMPLOYEE.getQuery());
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();
                System.out.println("Deleted employee with referred Id");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Optional<Employee> insertEmployee(final Employee employee) {
        try {final Connection connection = JdbcConnection.connect() ;
            final PreparedStatement saveStatement = connection.prepareStatement(EmployeeQuery.INSERT_EMPLOYEE.getQuery());

            saveStatement.setInt(1, employee.getEmployeeNumber());
            setEmployeeParams(saveStatement, employee, 2);
            saveStatement.executeUpdate();

            System.out.println("Inserted Employee:");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return findById(employee.getEmployeeNumber());
    }

    private Optional<Employee> updateEmployee(final Employee employee) {
        try {
            final Connection connection = JdbcConnection.connect();
            final PreparedStatement updateStatement = connection.prepareStatement(EmployeeQuery.UPDATE_EMPLOYEE.getQuery());

            setEmployeeParams(updateStatement, employee, 1);
            updateStatement.setInt(8, employee.getEmployeeNumber());
            updateStatement.executeUpdate();

            System.out.println("Updated employee:");
            return findById(employee.getEmployeeNumber());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    private void setEmployeeParams(PreparedStatement ps, Employee employee, int startIndex) throws SQLException {
        ps.setString(startIndex, employee.getLastName());
        ps.setString(startIndex + 1, employee.getFirstName());
        ps.setString(startIndex + 2, employee.getExtension());
        ps.setString(startIndex + 3, employee.getEmail());
        ps.setString(startIndex + 4, employee.getOfficeCode());
        ps.setInt(startIndex + 5, employee.getReportsTo());
        ps.setString(startIndex + 6, employee.getJobTitle());
    }


}