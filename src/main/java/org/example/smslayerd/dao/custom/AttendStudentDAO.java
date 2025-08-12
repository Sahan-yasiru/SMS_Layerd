package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.AttendenceStu;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendStudentDAO  extends CRUDDao<AttendenceStu> {
    public String[] autoSaveItems(ArrayList...arrayLists) throws SQLException;
    public boolean setAttendance(String attendID, Boolean state)throws SQLException;
    public boolean ifExitSP(AttendenceStu attendenceStu) throws SQLException;

    public boolean deleteUseStu(String id) throws SQLException;
}
