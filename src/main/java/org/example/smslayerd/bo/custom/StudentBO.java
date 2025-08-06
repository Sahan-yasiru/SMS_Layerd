package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoClass;
import org.example.smslayerd.model.DtoStudent;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    public ArrayList<DtoStudent> getAll() throws SQLException;
    public boolean save(DtoStudent dto) throws SQLException;
    public boolean update(DtoStudent dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoStudent search(String id)throws SQLException;
    public String getNumber() throws SQLException;

}
