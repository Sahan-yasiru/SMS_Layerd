package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.Class;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClassDao extends CRUDDao<Class> {
    public ArrayList<Class> getClassIDS()throws SQLException;
}
