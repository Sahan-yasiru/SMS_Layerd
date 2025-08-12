package org.example.smslayerd.bo.custom;

import org.example.smslayerd.bo.SuperBO;
import org.example.smslayerd.model.DtoAttendenceStu;
import org.example.smslayerd.model.DtoAttendenceTea;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceTeaBO extends SuperBO {

    public ArrayList<DtoAttendenceTea> getAll() throws SQLException;
    public boolean save(DtoAttendenceTea dto) throws SQLException;
    public boolean update(DtoAttendenceTea dto) throws SQLException;
    public boolean delete(String id)throws SQLException;
    public boolean ifExit(String id)throws SQLException;
    public String getNewId()throws SQLException;
    public DtoAttendenceTea  search(String id)throws SQLException;
    public String getNumber() throws SQLException;
    public boolean setAttendance(String attendID, Boolean state)throws SQLException;
    public boolean ifExitSP(DtoAttendenceTea attendenceTea) throws SQLException;

}
