package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.AttendanceTeaBO;
import org.example.smslayerd.bo.custom.TeacherBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendTeacherDAO;
import org.example.smslayerd.dao.custom.TeacherDao;
import org.example.smslayerd.dao.custom.impl.AttendTeacherDAOImpl;
import org.example.smslayerd.dao.custom.impl.TeacherDAOImpl;
import org.example.smslayerd.db.DBController;
import org.example.smslayerd.entity.Teacher;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.model.DtoTeacher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherBOImpl implements TeacherBO {

    TeacherDao teacherDAO=(TeacherDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Teacher);
    AttendTeacherDAO attendTeacherDAO=(AttendTeacherDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceTea);



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
        return teacherDAO.update(new Teacher(dto.getTeacherID(),dto.getSubjectID(),dto.getName(),dto.getClassId(),dto.getGradeAssign()));
    }

    @Override
    public boolean save(DtoTeacher dto) throws SQLException {
        return teacherDAO.save(new Teacher(dto.getTeacherID(),dto.getSubjectID(),dto.getName(),dto.getClassId(),dto.getGradeAssign()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        boolean b;
        Connection connection = DBController.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            attendTeacherDAO.deleteUseTea(id);
            b=teacherDAO.delete(id);
            if(b){
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        } catch (Exception e) {
            connection.setAutoCommit(true);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return teacherDAO.getNewId();
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
