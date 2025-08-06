package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.ClassDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDAOImpl implements ClassDao {
    @Override
    public String getNumber() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT COUNT(Class_ID) FROM Class");
        set.next();
        return set.getString(1);
    }

    @Override
    public ArrayList<Class> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Class dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Class dto) throws SQLException {
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
    public Class search(String id) throws SQLException {
        return null;
    }
}
