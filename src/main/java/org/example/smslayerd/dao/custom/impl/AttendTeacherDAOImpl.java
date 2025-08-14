package org.example.smslayerd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.smslayerd.controller.LoginController;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendStudentDAO;
import org.example.smslayerd.dao.custom.AttendTeacherDAO;
import org.example.smslayerd.dao.custom.QueryDAO;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.entity.AttendenceTea;
import org.example.smslayerd.entity.TimeTable;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AttendTeacherDAOImpl implements AttendTeacherDAO, QueryDAO {

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.User);

    @Override
    public boolean deleteUseTea(String teacherId) throws SQLException {
        return CRUD.executeQuery("DELETE From Attendance_Tea where Teacher_ID = ?",teacherId);
    }

    @Override
    public boolean setAttendance(String attendID, Boolean state) throws SQLException {
        return false;
    }

    @Override
    public String[] autoSaveItems(ArrayList... arrayLists) throws SQLException {
        ArrayList<String> insertedLogs = new ArrayList<>();
        if (arrayLists == null || arrayLists.length < 2 || arrayLists[0] == null || arrayLists[1] == null) {
            return null;
        } else {
            ArrayList<String> teacherIDs = arrayLists[0];
            ArrayList<String> classIDs = arrayLists[1];

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (String teacherID : teacherIDs) {
                ResultSet classSet = CRUD.executeQuery("SELECT Class_ID FROM Teacher WHERE Teacher_ID = ?", teacherID);
                if (classSet.next()) {
                    String classID = classSet.getString("Class_ID");
                    ResultSet exists = CRUD.executeQuery(
                            "SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND Class_ID = ?", today, teacherID, classID);
                    if (!exists.next()) {
                        boolean inserted = CRUD.executeQuery("INSERT INTO Attendance_Tea VALUES (?, ?, ?, ?, ?, ?)",
                                getNewId(),
                                today,
                                userDAO.getAdminName(LoginController.getLabel()),
                                teacherID,
                                false,
                                classID);
                        if (inserted) {
                            insertedLogs.add(teacherID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }
        return insertedLogs.toArray(new String[0]);
    }

    @Override
    public boolean ifExitSP(AttendenceTea attendenceTea) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND Class_ID = ?", attendenceTea.getDate(), attendenceTea.getTeacherID(), attendenceTea.getClassID());
        return set.next();
    }

    @Override
    public ArrayList<AttendenceTea> getAll() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Tea");
        ArrayList<AttendenceTea> attendanceList = new ArrayList<>();

        while (set.next()) {
            AttendenceTea attendenceTea = new AttendenceTea();
            attendenceTea.setAttendID(set.getString(1));
            attendenceTea.setDate(set.getString(2));
            attendenceTea.setAdminID(set.getString(3));
            attendenceTea.setTeacherID(set.getString(4));
            attendenceTea.setToggleSwitch(new ToggleSwitch(set.getBoolean(5)));
            attendenceTea.setClassID(set.getString(6));

            attendanceList.add(attendenceTea);
        }

        return attendanceList;
    }

    @Override
    public boolean save(AttendenceTea ety) throws SQLException {
        String sql = "INSERT INTO Attendance_Tea VALUES (?, ?, ?, ?, ?, ? )";
        Boolean b = CRUD.executeQuery(sql, ety.getAttendID(), ety.getDate(), ety.getAdminID(), ety.getTeacherID(),
                ety.getStatus(), ety.getClassID());

        return b;
    }

    @Override
    public boolean update(AttendenceTea Ety) throws SQLException {
        String sql = "UPDATE Attendance_Tea SET Date = ? ,Admin_ID = ? ,Teacher_ID = ? ,Class_ID = ? , Status = ? WHERE Attend_ID = ?";
        Boolean b = CRUD.executeQuery(sql, Ety.getDate(), Ety.getAdminID(), Ety.getTeacherID(),
                Ety.getClassID(), Ety.getStatus(), Ety.getAttendID());

        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CRUD.executeQuery("DELETE FROM Attendance_Tea WHERE Attend_ID = ?", id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT  Attend_ID FROM Attendance_Tea ORDER BY Admin_ID DESC LIMIT 1");
        if (set.next()) {
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("AT" + "%03d", i);

        } else {
            return "AT001";
        }


    }

    @Override
    public AttendenceTea search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }

    @Override
    public ArrayList[] checkRegistered() throws SQLException {
        String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date());
        ResultSet teacherSet = CRUD.executeQuery(
                "SELECT DISTINCT t.Teacher_ID FROM Teacher t JOIN Class c ON t.Class_ID = c.Class_ID " +
                        "JOIN Time_Table tt ON c.Time_Table_ID = tt.Time_Table_ID WHERE tt.day_of_week = ?",
                dayOfWeek);

        ResultSet classSet = CRUD.executeQuery(
                "SELECT DISTINCT c.Class_ID FROM Class c JOIN Time_Table t ON c.Time_Table_ID = t.Time_Table_ID WHERE t.day_of_week = ?",
                dayOfWeek);

        ArrayList<String> teacherIDs = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (teacherSet.next()) {
            teacherIDs.add(teacherSet.getString(1));
        }

        while (classSet.next()) {
            classIDs.add(classSet.getString(1));
        }

        return new ArrayList[]{teacherIDs, classIDs};
    }

    @Override
    public ArrayList<TimeTable> getAllByDate() throws SQLException {
        return null;
    }
}
