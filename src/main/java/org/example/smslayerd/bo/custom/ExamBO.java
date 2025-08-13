package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoExam;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExamBO extends SuperBO {
    public ArrayList<DtoExam> getAll() throws SQLException;
    public boolean save(DtoExam dtoExam) throws SQLException;
    public boolean update(DtoExam dtoExam) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoExam search(String id)throws SQLException;
    public String getNumber() throws SQLException;
}
