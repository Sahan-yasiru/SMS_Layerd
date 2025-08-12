package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.TeacherDao;
import org.example.smslayerd.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAOImpl implements TeacherDao {
    @Override
    public String getNumber() throws SQLException {
        ResultSet set= CRUD.executeQuery("SELECT COUNT(Teacher_ID) FROM Teacher");
        set.next();
        return set.getString(1);    }

    @Override
    public ArrayList<Teacher> getAll() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT * FROM Teacher");
        ArrayList<Teacher> teachers=new ArrayList<>();
        while (set.next()){
            teachers.add(new Teacher(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getInt(5)));
        }
        return teachers;
    }

    @Override
    public boolean save(Teacher dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Teacher dto) throws SQLException {
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
    public Teacher search(String id) throws SQLException {
        return null;
    }
}
