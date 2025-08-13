package org.example.smslayerd.dao.custom.impl;


import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.ClassDao;
import org.example.smslayerd.entity.Class;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDAOImpl implements ClassDao {
    @Override
    public ArrayList<Class> getClassIDS() throws SQLException {
        String sql = "SELECT Class_ID FROM Class";
        ResultSet resultSet = CRUD.executeQuery(sql);
        ArrayList<Class> classIDs = new ArrayList<>();

        while (resultSet.next()) {
            classIDs.add(new Class(resultSet.getString(1), 0, null, null));
        }

        return classIDs;
    }

    @Override
    public String getNumber() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT COUNT(Class_ID) FROM Class");
        set.next();
        return set.getString(1);
    }

    @Override
    public ArrayList<Class> getAll() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Class");
        ArrayList<Class> classes = new ArrayList<>();
        while (set.next()) {
            classes.add(new Class(set.getString(1), set.getInt(2), set.getString(3), set.getString(4)));
        }
        return classes;
    }

    @Override
    public boolean save(Class ety) throws SQLException {
        String sql = "INSERT INTO Class VALUES (?,?,?,?)";
        return CRUD.executeQuery(sql, ety.getClassID(), ety.getGrade(), ety.getTimeTableID(), ety.getSubjectID());
    }

    @Override
    public boolean update(Class ety) throws SQLException {
        String sql = "UPDATE Class SET Grade= ? ,Time_Table_ID= ? ,Subject_ID=? WHERE Class_ID=?";
        return CRUD.executeQuery(sql, ety.getGrade(), ety.getTimeTableID(), ety.getSubjectID(), ety.getClassID());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CRUD.executeQuery("DELETE FROM Class where Class_ID=?",id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT  Class_ID FROM Class ORDER BY Class_ID DESC LIMIT 1");
        if (set.next()) {
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("C" + "%03d", i);

        } else {
            return "C001";
        }


    }

    @Override
    public Class search(String id) throws SQLException {
        return null;
    }
}
