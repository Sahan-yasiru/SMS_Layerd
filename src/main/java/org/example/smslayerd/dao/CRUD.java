package org.example.smslayerd.dao;

import org.example.smslayerd.db.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUD  {
    private static Connection connection;


    public static <T>T  executeQuery(String sql,Object ...object) throws SQLException{
        connection=DBController.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i <object.length ; i++) {
            preparedStatement.setObject(i+1,object[i]);
        }
        if(sql.startsWith("select")||sql.startsWith("SELECT")){
            ResultSet set=preparedStatement.executeQuery();
            return (T)set;
        }else {
            return preparedStatement.executeUpdate()>0 ?(T)(Boolean)true:(T)(Boolean)false;
        }


    }

}
