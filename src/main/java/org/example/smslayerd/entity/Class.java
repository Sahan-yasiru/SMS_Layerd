package org.example.smslayerd.entity;

public class Class {
    private String classID;
    private int grade;
    private String timeTableID;
    private String subjectID;

    public Class(String classID, int grade, String timeTableID, String subjectID) {
        this.classID = classID;
        this.grade = grade;
        this.timeTableID = timeTableID;
        this.subjectID = subjectID;
    }
    public Class(){}

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTimeTableID() {
        return timeTableID;
    }

    public void setTimeTableID(String timeTableID) {
        this.timeTableID = timeTableID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
}
