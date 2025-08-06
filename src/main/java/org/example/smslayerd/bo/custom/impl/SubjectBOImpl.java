package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.SubjectBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.impl.SubjectDAOImpl;
import org.example.smslayerd.model.DtoSubject;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectBOImpl implements SubjectBO {

    SubjectDAOImpl subjectDAO=(SubjectDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Subject);
    @Override
    public ArrayList<DtoSubject> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(DtoSubject dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(DtoSubject dto) throws SQLException {
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
        return subjectDAO.getNewId();
    }

    @Override
    public DtoSubject search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return subjectDAO.getNumber();
    }
}
