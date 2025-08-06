package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.UserBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.UserDao;
import org.example.smslayerd.dao.custom.impl.UserDAOImpl;
import org.example.smslayerd.entity.Admin;
import org.example.smslayerd.model.DtoAdmin;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDao loginUserDAO= (UserDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.User);

    @Override
    public String getPassword(String adminId) throws SQLException {
        return loginUserDAO.getPassword(adminId);
    }

    @Override
    public String getNumber() throws SQLException {
        return loginUserDAO.getNumber();
    }

    @Override
    public boolean adminUpdateWithType(DtoAdmin dtoAdmin, boolean type) throws SQLException {
        return loginUserDAO.adminUpdateWithType(new Admin(dtoAdmin.getAdminID(),dtoAdmin.getUserName(),dtoAdmin.getPassword(),dtoAdmin.getAdminType()),type);
    }

    @Override
    public boolean chackAdminType(String userName) throws SQLException {
        return loginUserDAO.chackAdminType(userName);
    }

    @Override
    public boolean save(DtoAdmin dto) throws SQLException {
        return loginUserDAO.save(new Admin(dto.getAdminID(),dto.getUserName(),dto.getPassword(),dto.getAdminType()));
    }

    @Override
    public ArrayList<DtoAdmin> getAll(boolean type) throws SQLException {
        ArrayList<DtoAdmin> admins=new ArrayList<>();
        loginUserDAO.getAll(type).forEach(admin -> {
            admins.add(new DtoAdmin(admin.getAdminID(),admin.getUserName(),admin.getPassword(),admin.getAdminType()));
        });
        return admins;
    }

    @Override
    public boolean update(DtoAdmin dto) throws SQLException {
        return false;
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
       return loginUserDAO.getNewId();
    }

    @Override
    public DtoAdmin search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return loginUserDAO.delete(id);
    }

    @Override
    public boolean chackLogin(String userName, String password) throws SQLException {
        return loginUserDAO.chackLogin(userName,password);
    }
}
