package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.AttendanceStuBO;
import org.example.smslayerd.controller.LoginController;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendStudentDAO;
import org.example.smslayerd.dao.custom.ClassDao;
import org.example.smslayerd.dao.custom.QueryDAO;
import org.example.smslayerd.dao.custom.UserDao;
import org.example.smslayerd.dao.custom.impl.AttendStudentDAOImpl;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.model.DtoAttendenceStu;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AttendanceStuBOImpl implements AttendanceStuBO {


    AttendStudentDAO attendStudentDAO = (AttendStudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceStu);
    QueryDAO queryDAO=(AttendStudentDAOImpl)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceStu);
    ClassDao classDao=(ClassDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Class);
    UserDao userDao=(UserDao)DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.User);

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
        ArrayList[] arrays=queryDAO.checkRegistered();

        ArrayList<String> studentIds=arrays[0];
        ArrayList<String> classIDs=arrays[1];

        boolean ifExit=true;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(today);
        for (String studentID : studentIds) {
            boolean b = true;
            for (String classID : classIDs) {
                b = attendStudentDAO.ifExitSP(new AttendenceStu(null, today, null, studentID, classID, null));
                if (!b) {
                    ifExit = false;
                    break;
                }
            }
            if (!b) {
                break;
            }
        }
        if(!ifExit) {
//            String[] logs = attendStudentDAO.autoSaveItems(arrays);
            for (String studentID : studentIds) {
                String b=classDao.chackStuAvl( studentID);
                if (!b.isEmpty()) {
                    String classID = b;
                    boolean b2 = attendStudentDAO.ifExitSP(new AttendenceStu(null, today, null, studentID, classID, null));
                    String adminName= userDao.getAdminName(LoginController.getLabel());
                    if (!b2) {
                        boolean inserted = attendStudentDAO.save(new AttendenceStu(attendStudentDAO.getNewId(), today, adminName, studentID, classID, new ToggleSwitch(false)));
                        if (inserted) {
                            System.out.println(studentID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }
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
