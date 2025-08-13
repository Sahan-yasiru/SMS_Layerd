package org.example.smslayerd.dao.custom.impl;

import org.example.smslayerd.controller.LoginController;
import org.example.smslayerd.dao.CRUD;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.AttendStudentDAO;
import org.example.smslayerd.dao.custom.QueryDAO;
import org.example.smslayerd.entity.AttendenceStu;
import org.example.smslayerd.entity.TimeTable;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AttendStudentDAOImpl implements AttendStudentDAO, QueryDAO {

    UserDAOImpl userDAO= (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.User);

    @Override
    public boolean deleteUseStu(String id) throws SQLException {

        boolean b= CRUD.executeQuery("DELETE FROM Attendance_Stu WHERE Student_ID = ?",id) ;
        System.out.println(b);
        return b;
    }

    @Override
    public boolean ifExitSP(AttendenceStu attendenceStu) throws SQLException {
       ResultSet set= CRUD.executeQuery("SELECT * FROM Attendance_Stu WHERE Date = ? AND Student_ID = ? AND Class_ID = ?", attendenceStu.getDate()==null?null:attendenceStu.getDate(), attendenceStu.getStudentID(), attendenceStu.getClassID()==null?null:attendenceStu.getClassID());
       return set.next();
    }

    @Override
    public ArrayList<TimeTable> getAllByDate() throws SQLException {
        return null;
    }

    @Override
    public ArrayList[] checkRegistered() throws SQLException {
        String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date());
        ResultSet teacherSet = CRUD.executeQuery(
                "SELECT DISTINCT S.Student_ID FROM Student S JOIN Class c ON c.Class_ID = S.Class_ID  " +
                        " JOIN Time_Table tt ON c.Time_Table_ID = tt.Time_Table_ID WHERE tt.day_of_week = ? ",
                dayOfWeek);

        ResultSet classSet = CRUD.executeQuery(
                "SELECT DISTINCT c.Class_ID FROM Class c JOIN Time_Table t ON c.Time_Table_ID = t.Time_Table_ID WHERE t.day_of_week = ?",
                dayOfWeek);

        ArrayList<String> studentIds = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (teacherSet.next()) {
            studentIds.add(teacherSet.getString(1));
        }

        while (classSet.next()) {
            classIDs.add(classSet.getString(1));
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (String studentID : studentIds) {
            for (String classID : classIDs) {
                ResultSet chackSet = CRUD.executeQuery("SELECT * FROM Attendance_Stu  WHERE Student_ID = ? AND Class_ID = ? AND Date = ?", studentID, classID, today);
                if (chackSet.next()) {
                    return null;
                }
            }
        }
        return new ArrayList[]{studentIds, classIDs};
    }

    @Override
    public ArrayList<AttendenceStu> getAll() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Stu");
        ArrayList<AttendenceStu> attendanceList = new ArrayList<>();

        while (set.next()) {
            attendanceList.add(new AttendenceStu(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    new ToggleSwitch(set.getBoolean(6))
            ));
        }

        return attendanceList;
    }

    @Override
    public boolean save(AttendenceStu attendenceStu) throws SQLException {
            String sql = "INSERT INTO Attendance_Stu VALUES (?, ?, ?, ?, ?, ? )";
            Boolean b = CRUD.executeQuery(sql, attendenceStu.getAttendID(),attendenceStu.getDate(), attendenceStu.getAdminID(), attendenceStu.getStudentID(),
                    attendenceStu.getClassID(), attendenceStu.getStatus());

            return b ;
    }

    @Override
    public boolean update(AttendenceStu attendenceStu) throws SQLException {
            String sql = "UPDATE Attendance_Stu SET Date = ? ,Admin_ID = ? ,Student_ID = ? ,Class_ID = ? , Status = ? WHERE Attend_ID = ?";
            Boolean b = CRUD.executeQuery(sql, attendenceStu.getDate(), attendenceStu.getAdminID(), attendenceStu.getStudentID(),
                    attendenceStu.getClassID(), attendenceStu.getStatus(), attendenceStu.getAttendID());

            return b;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CRUD.executeQuery("DELETE FROM Attendance_Stu WHERE Attend_ID = ?", id);

    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT  Attend_ID FROM Attendance_Stu ORDER BY Admin_ID DESC LIMIT 1");
        if(set.next()){
            int i = Integer.parseInt(set.getString(1).replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;
            return String.format("AT"+"%03d", i);

        }else {
            return "AT001";
        }


    }

    @Override
    public AttendenceStu search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getNumber() throws SQLException {
        return "";
    }

    @Override
    public String[] autoSaveItems(ArrayList... arrayLists) throws SQLException {
        ArrayList<String> insertedLogs = new ArrayList<>();
        if (arrayLists == null || arrayLists.length < 2 || arrayLists[0] == null || arrayLists[1] == null) {
            return null;
        } else {
            ArrayList<String> studentIDs = arrayLists[0];
            ArrayList<String> classIDs = arrayLists[1];

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (String studentID : studentIDs) {
                ResultSet classSet = CRUD.executeQuery("SELECT Class_ID FROM Student WHERE Student_ID = ?", studentID);
                if (classSet.next()) {
                    String classID = classSet.getString("Class_ID");
                    ResultSet exists = CRUD.executeQuery(
                            "SELECT * FROM Attendance_Stu WHERE Date = ? AND Student_ID = ? AND Class_ID = ?", today, studentID, classID);
                    String adminName= userDAO.getAdminName(LoginController.getLabel());
                    if (!exists.next()) {
                        boolean inserted = CRUD.executeQuery("INSERT INTO Attendance_Stu VALUES (?, ?, ?, ?, ?, ?)",
                                getNewId(),
                                today,
                                adminName,
                                studentID,
                                classID,
                                false);
                        if (inserted) {
                            insertedLogs.add(studentID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }
        return insertedLogs.toArray(new String[0]);
    }

    @Override
    public boolean setAttendance(String attendID, Boolean state) throws SQLException {
        String sql = "UPDATE Attendance_Stu SET Status = ? WHERE Attend_ID = ?";
        return CRUD.executeQuery(sql, state, attendID);
    }

}
