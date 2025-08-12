package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoClass;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClassBO extends SuperBO {
    public ArrayList<DtoClass> getAll() throws SQLException;
    public boolean save(DtoClass dto) throws SQLException;
    public boolean update(DtoClass dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoClass search(String id)throws SQLException;
    public String getNumber() throws SQLException;
    public ArrayList<DtoClass> getClassIDs()throws SQLException;

}
