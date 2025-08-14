package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.Teacher;

import java.sql.SQLException;

public interface TeacherDao extends CRUDDao<Teacher> {
    String chackTeaAvl(String id) throws SQLException;
}
