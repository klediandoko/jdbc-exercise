package com.lhind.internship.jdbc.model.enums;

public enum EmployeeQuery {

    SELECT_ALL("SELECT * FROM employees;"),
    SELECT_BY_ID("SELECT * FROM employees WHERE employeeNumber = ?;"),
    EMPLOYEE_EXISTS("SELECT EXISTS(SELECT 1 FROM employees WHERE employeeNumber = ?)"),
    INSERT_EMPLOYEE("Insert into employees(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) \n" +
            "values (?,? ,? ,? ,? ,? ,? ,? )"),
    UPDATE_EMPLOYEE("Update employees set lastName= ?, firstName= ?, extension= ?, email = ?, officeCode= ?, " +
            "reportsTo = ?, jobTitle= ? where employeeNumber = ?"),
    DELETE_EMPLOYEE("delete from employees where employeeNumber = ?;");


    private final String query;

    EmployeeQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
