package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.ExamBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.ExamDAO;
import org.example.smslayerd.dao.custom.impl.ExamDAOImpl;
import org.example.smslayerd.entity.Exam;
import org.example.smslayerd.model.DtoExam;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExamBOImpl implements ExamBO {

    ExamDAO examDAO=(ExamDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Exam);
    @Override
    public ArrayList<DtoExam> getAll() throws SQLException {
        ArrayList<DtoExam> dtoExams=new ArrayList<>();
         examDAO.getAll().forEach(exam -> {
             dtoExams.add(new DtoExam(exam));
         });
         return dtoExams;
    }

    @Override
    public boolean save(DtoExam dtoExam) throws SQLException {
        return examDAO.save(new Exam(dtoExam));
    }

    @Override
    public boolean update(DtoExam dtoExam) throws SQLException {
        return examDAO.update(new Exam(dtoExam));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return examDAO.delete(id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return examDAO.getNewId();
    }

    @Override
    public DtoExam search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }
}
