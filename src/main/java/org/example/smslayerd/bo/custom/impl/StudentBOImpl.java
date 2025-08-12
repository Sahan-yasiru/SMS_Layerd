package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.StudentBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendStudentDAO;
import org.example.smslayerd.dao.custom.StudentDao;
import org.example.smslayerd.db.DBController;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.entity.Student;
import org.example.smslayerd.model.DtoStudent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    StudentDao studentDAO = (StudentDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Student);
    AttendStudentDAO attendStudentDAO = (AttendStudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceStu);

    @Override
    public ArrayList<DtoStudent> getStudentIDs() throws SQLException {
        ArrayList<DtoStudent> dtoStudents = new ArrayList<>();
        studentDAO.getStudentIDs().forEach(student -> {
            dtoStudents.add(new DtoStudent(student.getStudentID(), 0, null, null, 0, null));
        });
        return dtoStudents;
    }

    @Override
    public ArrayList<DtoStudent> getAll() throws SQLException {
        ArrayList<DtoStudent> students = new ArrayList<>();
        studentDAO.getAll().forEach(student -> {
            students.add(new DtoStudent(student));
        });
        return students;
    }

    @Override
    public boolean save(DtoStudent dto) throws SQLException {
        return studentDAO.save(new Student(dto));
    }

    @Override
    public boolean update(DtoStudent dto) throws SQLException {
        return studentDAO.update(new Student(dto));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        boolean b;
        Connection connection = DBController.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            attendStudentDAO.deleteUseStu(id);
            b=studentDAO.delete(id);
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
        return studentDAO.getNewId();
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
