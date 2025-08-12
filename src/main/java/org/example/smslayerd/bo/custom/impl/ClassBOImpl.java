package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.ClassDao;
import org.example.smslayerd.dao.custom.impl.ClassDAOImpl;
import org.example.smslayerd.model.DtoClass;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClassBOImpl implements ClassBO {
    ClassDao classDAO=(ClassDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Class);

    @Override
    public ArrayList<DtoClass> getClassIDs() throws SQLException {
        ArrayList<DtoClass> dtoClasses=new ArrayList<>();
        classDAO.getClassIDS().forEach(dtoClass->{
            dtoClasses.add(new DtoClass(dtoClass.getClassID(),dtoClass.getGrade(),dtoClass.getTimeTableID(),dtoClass.getSubjectID()));
        });
        return dtoClasses;
    }

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
