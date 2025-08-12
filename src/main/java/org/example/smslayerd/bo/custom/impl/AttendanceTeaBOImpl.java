package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.AttendanceTeaBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendTeacherDAO;
import org.example.smslayerd.dao.custom.impl.AttendTeacherDAOImpl;
import org.example.smslayerd.entity.AttendenceTea;
import org.example.smslayerd.model.DtoAttendenceTea;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceTeaBOImpl implements AttendanceTeaBO {
    AttendTeacherDAO attendTeacherDAO =(AttendTeacherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceTea);
    @Override
    public ArrayList<DtoAttendenceTea> getAll() throws SQLException {
        ArrayList<DtoAttendenceTea> attendenceTeas=new ArrayList<>();
        attendTeacherDAO.getAll().forEach(attendenceTea -> {
            attendenceTeas.add(new DtoAttendenceTea(attendenceTea));
        });
        return attendenceTeas;
    }

    @Override
    public boolean save(DtoAttendenceTea dto) throws SQLException {
        boolean b=ifExitSP(dto);
        if(!b) {
            return attendTeacherDAO.save(new AttendenceTea(dto));
        }
        return false;
    }

    @Override
    public boolean update(DtoAttendenceTea dto) throws SQLException {
        return attendTeacherDAO.update(new AttendenceTea(dto));
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return attendTeacherDAO.delete(id);
    }

    @Override
    public String getNewId() throws SQLException {
       return attendTeacherDAO.getNewId();
    }

    @Override
    public DtoAttendenceTea search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }

    @Override
    public boolean setAttendance(String attendID, Boolean state) throws SQLException {
        return attendTeacherDAO.setAttendance(attendID,state);
    }

    @Override
    public boolean ifExitSP(DtoAttendenceTea attendenceTea) throws SQLException {
        return attendTeacherDAO.ifExitSP(new AttendenceTea(attendenceTea));
    }
}
