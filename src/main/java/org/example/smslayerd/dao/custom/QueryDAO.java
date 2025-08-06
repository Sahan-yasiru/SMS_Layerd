package org.example.smslayerd.dao.custom;


import org.example.smslayerd.dao.SuperDao;
import org.example.smslayerd.entity.TimeTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDao {
    public ArrayList<TimeTable> getAllByDate()throws SQLException;

}
