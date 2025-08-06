package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.impl.ClassDAOImpl;
import org.example.smslayerd.model.DtoClass;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClassBOImpl implements ClassBO {
    ClassDAOImpl classDAO=(ClassDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Class);
    @Override
    public ArrayList<DtoClass> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(DtoClass dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(DtoClass dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return "";
    }

    @Override
    public DtoClass search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return classDAO.getNumber();
    }
}
