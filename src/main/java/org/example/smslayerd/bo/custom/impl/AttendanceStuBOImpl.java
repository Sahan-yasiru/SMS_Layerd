package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.AttendanceStuBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendStudentDAO;
import org.example.smslayerd.dao.custom.impl.AttendStudentDAOImpl;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.model.DtoAttendenceStu;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceStuBOImpl implements AttendanceStuBO {


    AttendStudentDAO attendStudentDAO = (AttendStudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceStu);

    @Override
    public boolean ifExitSP(DtoAttendenceStu attendenceStu) throws SQLException {
        return attendStudentDAO.ifExitSP(new AttendenceStu(attendenceStu));
    }

    @Override
    public boolean setAttendance(String attendID, Boolean state) throws SQLException {
        return attendStudentDAO.setAttendance(attendID, state);
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<DtoAttendenceStu> getAll() throws SQLException {
        ArrayList<AttendenceStu> attendenceStusETY = attendStudentDAO.getAll();
        ArrayList<DtoAttendenceStu> dtoAttendenceStus = new ArrayList<>();
        for (AttendenceStu attendenceStu : attendenceStusETY) {
            dtoAttendenceStus.add(new DtoAttendenceStu(attendenceStu));
        }
        return dtoAttendenceStus;


    }

    @Override
    public boolean save(DtoAttendenceStu dto) throws SQLException {
        boolean b = ifExitSP(dto);
        if (!b) {
            return attendStudentDAO.save(new AttendenceStu(dto));
        }
        return false;

    }

    @Override
    public boolean update(DtoAttendenceStu dto) throws SQLException {
        return attendStudentDAO.update(new AttendenceStu(dto));
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return attendStudentDAO.delete(id);
    }

    @Override
    public String getNewId() throws SQLException {
        return attendStudentDAO.getNewId();
    }

    @Override
    public DtoAttendenceStu search(String id) throws SQLException {
        return null;
    }

}
