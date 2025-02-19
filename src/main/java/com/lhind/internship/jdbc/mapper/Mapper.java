package com.lhind.internship.jdbc.mapper;

import com.lhind.internship.jdbc.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<ENTITY> {

    ENTITY toEntity(final ResultSet result) throws SQLException;

}
