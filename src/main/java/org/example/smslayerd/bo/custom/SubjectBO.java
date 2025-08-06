package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.dao.custom.SubjectDao;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.model.DtoSubject;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SubjectBO extends SuperBO {
    public ArrayList<DtoSubject> getAll() throws SQLException;
    public boolean save(DtoSubject dto) throws SQLException;
    public boolean update(DtoSubject dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoSubject search(String id)throws SQLException;
    public String getNumber() throws SQLException;
}
