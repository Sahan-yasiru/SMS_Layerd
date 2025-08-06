package org.example.smslayerd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        return CRUD.executeQuery("INSERT INTO Subject values (?,?)",dto.getSubjectID(),dto.getName());
    }

    @Override
    public ArrayList<Subject> getAll() throws SQLException {
        String sql = "SELECT * FROM Subject";
        ResultSet set = CRUD.executeQuery(sql);
        ArrayList<Subject> subjects =new ArrayList<>();
        while (set.next()) {
            subjects.add(new Subject(set.getString("Subject_ID"), set.getString("Name")));
        }
        return subjects;

    }

    @Override
    public boolean update(Subject dto) throws SQLException {
        return CRUD.executeQuery("UPDATE Subject SET Name=? WHERE Subject_ID=?",dto.getName(),dto.getSubjectID());
    }

    @Override
    public boolean delete(String id) throws SQLException {
       return CRUD.executeQuery("DELETE FROM Subject WHERE Subject_id=?",id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Subject_ID FROM Subject ORDER BY Subject_ID DESC LIMIT 1;");
        if(set.next()){
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("S"+"%03d", i);

        }else {
            return "S001";
        }


    }

    @Override
    public Subject search(String id) throws SQLException {
        String sql = "SELECT  * FROM Subject WHERE Subject_ID= ? OR Name= ?";

        ResultSet set = CRUD.executeQuery(sql, id, id);

        if(set.next()) {
            return new Subject(set.getString("Subject_ID"), set.getString("Name"));
        }
        return new Subject(null,null);

    }
}
