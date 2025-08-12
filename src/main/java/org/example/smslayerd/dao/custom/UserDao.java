package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.Admin;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao extends CRUDDao<Admin> {
    public boolean chackLogin(String userName, String password) throws SQLException;

    public boolean chackAdminType(String userName) throws SQLException;

    public boolean adminUpdateWithType(Admin admin, boolean type) throws SQLException;

    ArrayList<Admin> getAll(boolean type) throws SQLException;

    String getPassword(String adminId) throws SQLException;
    public String getAdminName(String id) throws SQLException;

}