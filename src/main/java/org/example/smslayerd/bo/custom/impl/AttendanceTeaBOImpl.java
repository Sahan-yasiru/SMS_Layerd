package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.AttendanceTeaBO;
import org.example.smslayerd.controller.LoginController;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.*;
import org.example.smslayerd.dao.custom.impl.AttendTeacherDAOImpl;
import org.example.smslayerd.entity.AttendenceTea;
import org.example.smslayerd.model.DtoAttendenceTea;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceTeaBOImpl implements AttendanceTeaBO {

    AttendTeacherDAO attendTeacherDAO = (AttendTeacherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceTea);
    QueryDAO queryDAO = (AttendTeacherDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AttendanceTea);
    TeacherDao teacherDao = (TeacherDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Teacher);
    UserDao userDao = (UserDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.User);


    @Override
    public ArrayList<DtoAttendenceTea> getAll() throws SQLException {

        ArrayList[] arrays = queryDAO.checkRegistered();

        ArrayList<String> teacherIDs = arrays[0];
        ArrayList<String> classIDs = arrays[1];

        boolean ifExit = true;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(today);
        for (String teacherID : teacherIDs) {
            boolean b = true;
            for (String classID : classIDs) {
                b = attendTeacherDAO.ifExitSP(new AttendenceTea(null, today, null, teacherID, null, classID));
                if (!b) {
                    ifExit = false;
                    break;
                }
            }
            if (!b) {
                break;
            }
        }
        if (!ifExit) {
//            String[] logs = attendStudentDAO.autoSaveItems(arrays);
            for (String teacherID : teacherIDs) {
                String b = teacherDao.chackTeaAvl(teacherID);
                if (!b.isEmpty()) {
                    String classID = b;
                    boolean b2 = attendTeacherDAO.ifExitSP(new AttendenceTea(null, today, null, teacherID, null, classID));
                    String adminName = userDao.getAdminName(LoginController.getLabel());
                    if (!b2) {
                        boolean inserted = attendTeacherDAO.save(new AttendenceTea(attendTeacherDAO.getNewId(), today, adminName, teacherID, new ToggleSwitch(false), classID));
                        if (inserted) {
                            System.out.println(teacherID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }

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
