package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoAttendenceStu;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceTeaBO extends SuperBO {

    public ArrayList<DtoAttendenceStu> getAll() throws SQLException;
    public boolean save(DtoAttendenceStu dto) throws SQLException;
    public boolean update(DtoAttendenceStu dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoAttendenceStu  search(String id)throws SQLException;
    public String getNumber() throws SQLException;
    public boolean setAttendance(String attendID, Boolean state)throws SQLException;
    public boolean ifExitSP(DtoAttendenceStu attendenceStu) throws SQLException;
}
