package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoAdmin;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO  extends SuperBO {
    public boolean chackLogin(String userName,String password) throws SQLException;
    public ArrayList<DtoAdmin> getAll(boolean type) throws SQLException;
    public boolean save(DtoAdmin dto) throws SQLException;
    public boolean update(DtoAdmin dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoAdmin search(String id)throws SQLException;
    public boolean chackAdminType(String userName) throws SQLException;
    public boolean adminUpdateWithType(DtoAdmin dtoAdmin, boolean type) throws SQLException;
    public String getNumber() throws SQLException;
    String getPassword(String adminId) throws SQLException;
    public String getAdminName(String id) throws SQLException;
}
