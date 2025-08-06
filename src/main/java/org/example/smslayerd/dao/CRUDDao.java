package org.example.smslayerd.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDDao<T> extends SuperDao{

    public ArrayList<T> getAll() throws SQLException;
    public boolean save(T dto) throws SQLException;
    public boolean update(T dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public T search(String id)throws SQLException;
    public String getNumber() throws SQLException;


}
