package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.TeacherBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.TeacherDao;
import org.example.smslayerd.dao.custom.impl.TeacherDAOImpl;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.model.DtoTeacher;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherBOImpl implements TeacherBO {

    TeacherDao teacherDAO=(TeacherDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Teacher);



    @Override
    public ArrayList<DtoTeacher> getAll() throws SQLException {
        ArrayList<DtoTeacher> dtoTeachers=new ArrayList<>();
        teacherDAO.getAll().forEach(teacher -> {
            dtoTeachers.add(new DtoTeacher(teacher));
        });
        return dtoTeachers;
    }

    @Override
    public boolean update(DtoTeacher dto) throws SQLException {
        return false;
    }

    @Override
    public boolean save(DtoTeacher dto) throws SQLException {
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
    public DtoTeacher search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return teacherDAO.getNumber();
    }
}
