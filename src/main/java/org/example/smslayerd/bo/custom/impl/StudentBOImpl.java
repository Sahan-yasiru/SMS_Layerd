package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.StudentBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.impl.StudentDAOImpl;
import org.example.smslayerd.model.DtoStudent;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl  implements StudentBO {

    StudentDAOImpl studentDAO= (StudentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Student);
    @Override
    public ArrayList<DtoStudent> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(DtoStudent dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(DtoStudent dto) throws SQLException {
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
    public DtoStudent search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return studentDAO.getNumber();
    }
}
