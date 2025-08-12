package org.example.smslayerd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.StudentDao;
import org.example.smslayerd.db.DBController;
import org.example.smslayerd.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDao {

    @Override
    public ArrayList<Student> getStudentIDs() throws SQLException {
        String sql = "SELECT Student_ID FROM Student";
        ResultSet resultSet = CRUD.executeQuery(sql);
        ArrayList<Student> studentIDs = new ArrayList<>();

        while (resultSet.next()) {
            studentIDs.add(new Student(resultSet.getString(1),0,null,null,0,null));
        }

        return studentIDs;
    }

    @Override
    public String getNumber() throws SQLException {
        ResultSet set= CRUD.executeQuery("SELECT COUNT(Student_ID) FROM Student");
         set.next();
         return set.getString(1);
    }

    @Override
    public ArrayList<Student> getAll() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT * FROM Student");
        ArrayList<Student> students= new ArrayList<>();
        while (set.next()){
            students.add(new Student(set.getString(1),set.getInt(2),set.getString(3),set.getString(4),set.getInt(5),set.getString(6)));
        }
        return students;
    }

    @Override
    public boolean save(Student dtoStudent) throws SQLException {
        String sql="INSERT INTO Student VALUES(?,?,?,?,?,?)";
        return CRUD.executeQuery(sql,dtoStudent.getStudentID(),dtoStudent.getTelNO(),dtoStudent.getClassID(),dtoStudent.getName(),dtoStudent.getGrade(),dtoStudent.getAddress());
    }

    @Override
    public boolean update(Student dtoStudent) throws SQLException {
        String sql="UPDATE Student SET Tel_No= ? ,Class_ID = ?,Name= ? ,Grade = ? ,Address = ?   WHERE  Student_ID = ?";
        return CRUD.executeQuery(sql,dtoStudent.getTelNO(),dtoStudent.getClassID(),dtoStudent.getName(),dtoStudent.getGrade(),dtoStudent.getAddress(),dtoStudent.getStudentID());

    }

    @Override
    public boolean delete(String id) throws SQLException {
          return   CRUD.executeQuery("DELETE FROM Student WHERE Student_ID = ?",id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT  Student_ID FROM Student ORDER BY Student_ID DESC LIMIT 1");
        if (set.next()) {
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("S" + "%03d", i);

        } else {
            return "S001";
        }


    }

    @Override
    public Student search(String id) throws SQLException {
        return null;
    }
}
