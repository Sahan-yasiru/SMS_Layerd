package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.model.DtoTeacher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TeacherBO extends SuperBO {
    public ArrayList<DtoTeacher> getAll() throws SQLException;
    public boolean save(DtoTeacher dto) throws SQLException;
    public boolean update(DtoTeacher dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoTeacher search(String id)throws SQLException;
    public String getNumber() throws SQLException;

}
