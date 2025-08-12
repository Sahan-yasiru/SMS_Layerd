package org.example.smslayerd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.smslayerd.controller.LoginController;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.UserDao;
import org.example.smslayerd.entity.Admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDao {

    @Override
    public String getAdminName(String id) throws SQLException {
        String sql = "SELECT Admin_ID FROM Admin WHERE User_name = ?";
        ResultSet resultSet = CRUD.executeQuery(sql, id);
        String result = "";

        while (resultSet.next()) {
            result = resultSet.getString(1);
        }

        return result;
    }

    @Override
    public String getPassword(String adminId) throws SQLException {

        ResultSet set = CRUD.executeQuery("SELECT Password FROM Admin WHERE Admin_ID = ? ", adminId);
        return set.next() ? set.getString(1) : null;

    }

    @Override
    public ArrayList<Admin> getAll(boolean type) throws SQLException {
        ResultSet set = null;
        String sql = "SELECT * FROM Admin ";
        if (type) {
            set = CRUD.executeQuery(sql);
        } else {
            sql += "WHERE User_Name = ?";
            set = CRUD.executeQuery(sql, LoginController.getLabel());
        }
        ArrayList<Admin> adminsEnys = new ArrayList<>();
        while (set.next()) {
            String id = set.getString(1);
            String name = set.getString(2);
            String password = getNumberOfStarts(id);
            String typeS = set.getString(4);

            adminsEnys.add(new Admin(id, name, password, typeS));
        }
        return adminsEnys;
    }
    public String getNumberOfStarts(String id) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Password FROM Admin WHERE Admin_ID = ?", id);
        String star = "";
        while (set.next()) {
            for (int i = 0; i < set.getString(1).length(); i++) {
                star += "*";
            }
        }
        return star;


    }

    @Override
    public String getNumber() throws SQLException {
        ResultSet resultSet = CRUD.executeQuery( "SELECT COUNT(Admin_ID) AS Number_of FROM Admin");
        String result = "";
        if (resultSet.next()) {
            result = resultSet.getString("Number_of");
        }
        return result;

    }

    @Override
    public boolean adminUpdateWithType(Admin admin, boolean type) throws SQLException {
        if (type) {
            String sql = "UPDATE Admin SET User_name=?,Password=?,AdminType= ?  WHERE Admin_ID=?";
            return CRUD.executeQuery(sql, admin.getUserName(), admin.getPassword(), admin.getAdminType(), admin.getAdminID());

        }
        String sql = "UPDATE Admin SET User_name=?,Password=?,AdminType= ?  WHERE Admin_ID=?";
        return CRUD.executeQuery(sql, admin.getUserName(), admin.getPassword(), admin.getAdminType(), admin.getAdminID());

    }

    @Override
    public boolean chackAdminType(String userName) throws SQLException {
        String sql = "SELECT AdminType FROM Admin WHERE User_name = ?";
        ResultSet set = CRUD.executeQuery(sql, userName);
        String result = null;
        while (set.next()) {
            result = set.getString(1);
        }
        return result.equals("SuperAdmin") ? true : false;
    }

    @Override
    public ArrayList<Admin> getAll() {
        return null;
    }

    @Override
    public boolean save(Admin adminEty) throws SQLException {
        return CRUD.executeQuery("insert into  Admin values (?,?,?,?)",adminEty.getAdminID(),adminEty.getUserName(),adminEty.getPassword(),adminEty.getAdminType());
    }

    @Override
    public boolean update(Admin dto) {
        return false;
    }

    @Override
    public boolean delete(String id)throws SQLException {
          return CRUD.executeQuery( "DELETE FROM Admin WHERE Admin_ID=?",id);

    }

    @Override
    public boolean ifExit(String id) {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set=CRUD.executeQuery("select Admin_ID from Admin order by Admin_ID desc limit 1");
        if(set.next()){
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("A"+"%03d", i);

        }else {
            return "A001";
        }


    }

    @Override
    public Admin search(String id) {
        return null;
    }

    @Override
    public boolean chackLogin(String userName, String password) throws SQLException {

            String sql="SELECT User_name,Password FROM Admin WHERE User_name=? AND Password=?";
            ResultSet resultSet= CRUD.executeQuery(sql,userName,password);

            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }


    }


}
