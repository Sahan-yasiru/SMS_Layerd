package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.SubjectDao;
import org.example.smslayerd.entity.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectDAOImpl implements SubjectDao {
    @Override
    public String getNumber() throws SQLException {
        ResultSet set= CRUD.executeQuery("SELECT COUNT(Subject_ID) FROM Subject");
        set.next();
        return set.getString(1);    }

    @Override
    public boolean save(Subject dto) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Subject> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Subject dto) throws SQLException {
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
        ResultSet set=CRUD.executeQuery("select Admin_ID from Admin order by Admin_ID desc limit 1");
        if(set.next()){
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("A"+"%03d", i);

        }else {
            return "A001";
        }


    }

    @Override
    public Subject search(String id) throws SQLException {
        return null;
    }
}
