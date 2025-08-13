package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.ExamDAO;
import org.example.smslayerd.entity.Exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDAOImpl implements ExamDAO {
    @Override
    public boolean save(Exam ety) throws SQLException {
        String sql="INSERT INTO Exam VALUES (?,?,?,?,?,?)";
        return CRUD.executeQuery(sql,ety.getExamID(),ety.getExmaDate(),ety.getMarks(),ety.getSubjectID(),ety.getTeacherID(),ety.getStudentID());
    }

    @Override
    public ArrayList<Exam> getAll() throws SQLException {
        String sql = "SELECT * FROM Exam";
        ResultSet set= CRUD.executeQuery(sql);
        ArrayList<Exam> list =new ArrayList<>();
        while (set.next()){
            Exam exam = new Exam();
            exam.setExamID(set.getString(1));
            exam.setExmaDate(set.getString(2));
            exam.setMarks(set.getInt(3));
            exam.setSubjectID(set.getString(4));
            exam.setTeacherID(set.getString(5));
            exam.setStuentID(set.getString(6));
            list.add(exam);
        }
        return list;
    }

    @Override
    public boolean update(Exam ety) throws SQLException {
        String sql="UPDATE Exam SET Subject_ID= ? , Student_ID = ?,Exam_Date = ?,Teacher_ID = ? ,Marks =  ? WHERE Exam_ID = ?";
        return CRUD.executeQuery(sql,ety.getSubjectID(),ety.getStudentID(),ety.getExmaDate(),ety.getTeacherID(),ety.getMarks(),ety.getExamID());

    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql="DELETE FROM Exam WHERE Exam_ID = ?";
        return CRUD.executeQuery(sql,id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT  Exam_ID FROM Exam ORDER BY Exam_ID DESC LIMIT 1");
        if (set.next()) {
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("EX" + "%03d", i);

        } else {
            return "EX001";
        }


    }

    @Override
    public Exam search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }
}
