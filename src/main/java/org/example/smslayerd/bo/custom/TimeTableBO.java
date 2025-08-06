package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.entity.TimeTable;
import org.example.smslayerd.model.DtoTeacher;
import org.example.smslayerd.model.DtoTimeTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeTableBO extends SuperBO {
    public ArrayList<DtoTimeTable> getAll() throws SQLException;
    public boolean save(DtoTimeTable dto) throws SQLException;
    public boolean update(DtoTimeTable dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoTimeTable search(String id)throws SQLException;
    public ArrayList<DtoTimeTable> getAllByDate()throws SQLException;
}
