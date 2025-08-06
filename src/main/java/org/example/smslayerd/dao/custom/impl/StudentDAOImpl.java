package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.StudentDao;
import org.example.smslayerd.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDao {
    @Override
    public String getNumber() throws SQLException {
        ResultSet set= CRUD.executeQuery("SELECT COUNT(Student_ID) FROM Student");
         set.next();
         return set.getString(1);
    }

    @Override
    public ArrayList<Student> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Student dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Student dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return "";
    }

    @Override
    public Student search(String id) throws SQLException {
        return null;
    }
}
