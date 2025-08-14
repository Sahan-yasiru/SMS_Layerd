package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.custom.TeacherDao;
import org.example.smslayerd.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAOImpl implements TeacherDao {


    @Override
    public String chackTeaAvl(String id) throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Class_ID FROM Teacher WHERE Teacher_ID = ?",id);
        return set.next()?set.getString(1):null;
    }

    @Override
    public String getNumber() throws SQLException {
        ResultSet set= CRUD.executeQuery("SELECT COUNT(Teacher_ID) FROM Teacher");
        set.next();
        return set.getString(1);    }

    @Override
    public ArrayList<Teacher> getAll() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT * FROM Teacher");
        ArrayList<Teacher> teachers=new ArrayList<>();
        while (set.next()){
            teachers.add(new Teacher(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getInt(5)));
        }
        return teachers;
    }

    @Override
    public boolean save(Teacher ety) throws SQLException {
        String sql="INSERT INTO Teacher VALUES(?,?,?,?,?)";
        return CRUD.executeQuery(sql,ety.getTeacherID(),ety.getSubjectID(),ety.getName(),
                ety.getClassId(),ety.getGradeAssign());
    }

    @Override
    public boolean update(Teacher ety) throws SQLException {
        String sql="UPDATE Teacher SET Name= ? ,Class_ID = ? ,Grades_Assigned= ?,Subject_ID = ?  WHERE Teacher_ID = ?";
        return CRUD.executeQuery(sql,ety.getName(),ety.getClassId(),ety.getGradeAssign(),ety.getSubjectID(),ety.getTeacherID());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CRUD.executeQuery("DELETE FROM Teacher WHERE Teacher_ID = ?",id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Time_Table_ID FROM Time_Table ORDER BY Time_Table_ID DESC LIMIT 1;");
        if(set.next()){
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("T"+"%03d", i);

        }else {
            return "T001";
        }
    }

    @Override
    public Teacher search(String id) throws SQLException {
        return null;
    }


}
