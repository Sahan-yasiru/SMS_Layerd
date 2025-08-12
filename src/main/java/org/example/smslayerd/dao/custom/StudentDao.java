package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentDao extends CRUDDao<Student> {
public ArrayList<Student> getStudentIDs() throws SQLException;
}
