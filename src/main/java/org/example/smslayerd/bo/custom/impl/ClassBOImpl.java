package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.ClassDao;
import org.example.smslayerd.dao.custom.impl.ClassDAOImpl;
import org.example.smslayerd.db.DBController;
import org.example.smslayerd.entity.Class;
import org.example.smslayerd.model.DtoClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassBOImpl implements ClassBO {
    ClassDao classDAO=(ClassDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Class);

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
        ArrayList<DtoClass> dtoClasses=new ArrayList<>();
        classDAO.getAll().forEach(aClass -> {
            dtoClasses.add(new DtoClass(aClass.getClassID(),aClass.getGrade(),aClass.getTimeTableID(),aClass.getSubjectID()));
        });
        return dtoClasses;
    }

    @Override
    public boolean save(DtoClass dto) throws SQLException {
        return classDAO.save(new Class(dto.getClassID(),dto.getGrade(),dto.getTimeTableID(),dto.getSubjectID()));
    }

    @Override
    public boolean update(DtoClass dto) throws SQLException {
        return classDAO.update(new Class(dto.getClassID(),dto.getGrade(),dto.getTimeTableID(),dto.getSubjectID()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return classDAO.delete(id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return classDAO.getNewId();
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
