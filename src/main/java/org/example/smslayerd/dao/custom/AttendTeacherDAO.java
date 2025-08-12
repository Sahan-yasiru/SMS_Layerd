package org.example.smslayerd.dao.custom;

import org.example.smslayerd.dao.CRUDDao;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.entity.AttendenceTea;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendTeacherDAO extends CRUDDao<AttendenceTea>  {
    public String[] autoSaveItems(ArrayList...arrayLists) throws SQLException;
    public boolean setAttendance(String attendID, Boolean state)throws SQLException;
    public boolean ifExitSP(AttendenceTea attendenceTea) throws SQLException;
    public boolean deleteUseTea(String teacherId) throws SQLException;


}
