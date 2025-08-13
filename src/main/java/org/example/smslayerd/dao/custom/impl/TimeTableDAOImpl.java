package org.example.smslayerd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.QueryDAO;
import org.example.smslayerd.dao.custom.TimeTableDao;
import org.example.smslayerd.entity.TimeTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimeTableDAOImpl implements TimeTableDao, QueryDAO {
    @Override
    public ArrayList[] checkRegistered() throws SQLException {
        return new ArrayList[0];
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<TimeTable> getAllByDate() throws SQLException {
        LocalDate localDate=LocalDate.now();
        DayOfWeek dayOfWeek=localDate.getDayOfWeek();
        String date=dayOfWeek.name();
        String sql="SELECT DISTINCT t.day_of_week,Subject.Name,t.start_time,t.End_time  FROM Time_Table,Subject join Time_Table t on Subject.Subject_ID = t.Subject_ID having t.day_of_week=?";
        ResultSet set= CRUD.executeQuery(sql,date);
        ArrayList<TimeTable> timeTablesEny= new ArrayList<>();
        while (set.next()){
            timeTablesEny.add(new TimeTable(null,null,set.getString("start_time"),set.getString("End_time"),set.getString("Day_of_week"),set.getString("Name")));
        }
        return timeTablesEny;
    }

    @Override
    public ArrayList<TimeTable> getAll() throws SQLException {
        String sql="SELECT * FROM Time_Table";
        ResultSet set= CRUD.executeQuery(sql);
        ArrayList<TimeTable> list = new ArrayList<>();
        while (set.next()){
            list.add(new TimeTable(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5),null));
        }
        return list;
    }

    @Override
    public boolean save(TimeTable dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TimeTable dto) throws SQLException {
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
    public TimeTable search(String id) throws SQLException {
        return null;
    }
}
