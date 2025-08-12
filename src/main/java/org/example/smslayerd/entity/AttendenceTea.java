package org.example.smslayerd.entity;

import org.example.smslayerd.model.DtoAttendenceTea;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;
public class AttendenceTea {
    private String attendID;
    private String Date;
    private String adminID;
    private String teacherID;
    private String classID;
    private ToggleSwitch toggleSwitch;
    private Boolean state;

    public AttendenceTea(String attendID, String date, String adminID, String teacherID, ToggleSwitch toggleSwitch, String classID) {
        this.attendID = attendID;
        this.Date = date;
        this.adminID = adminID;
        this.teacherID = teacherID;
        this.classID = classID;
        this.toggleSwitch = toggleSwitch;
    }
    public AttendenceTea() {}
    public AttendenceTea(DtoAttendenceTea dtoAttendenceTea) {
        this.attendID = dtoAttendenceTea.getAttendID();
        this.Date = dtoAttendenceTea.getDate();
        this.adminID = dtoAttendenceTea.getAdminID();
        this.teacherID = dtoAttendenceTea.getTeacherID();
        this.classID = dtoAttendenceTea.getClassID();
        this.toggleSwitch = dtoAttendenceTea.getToggleSwitch();
    }

    public String getAttendID() {
        return attendID;
    }

    public void setAttendID(String attendID) {
        this.attendID = attendID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String studentID) {
        this.teacherID = studentID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public Boolean getStatus() {
        return this.toggleSwitch.getSwitchedOn();
    }

    public void setStatus(Boolean status) {

        this.toggleSwitch.setSwitchedOn(status);
    }
    public ToggleSwitch getToggleSwitch() {
        return this.toggleSwitch;
    }
    public void setToggleSwitch(ToggleSwitch toggleSwitch){
        this.toggleSwitch=toggleSwitch;
    }

    @Override
    public String toString() {
        return this.attendID+" "+this.Date+" "+this.adminID+" "+this.teacherID+" "+this.classID+" "+this.getStatus();
    }
    public Boolean getState(){
        return this.state;
    }
}
