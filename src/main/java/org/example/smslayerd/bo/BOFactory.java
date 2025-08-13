package org.example.smslayerd.bo;

import org.example.smslayerd.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    public static BOFactory getInstance(){
        return boFactory==null?new BOFactory():boFactory;
    }
    public enum BOTypes{
        User,
        Class,
        Student,
        Teacher,
        Subject,
        TimeTable,
        AttendanceStu,
        AttendanceTea,
        Exam
    }
    public SuperBO getBOType(BOTypes boEnumType){
        switch (boEnumType){
            case User : return new UserBOImpl();
            case Class:return new ClassBOImpl();
            case Student:return new StudentBOImpl();
            case Teacher:return new TeacherBOImpl();
            case Subject:return new SubjectBOImpl();
            case TimeTable:return new TimeTableBOImpl();
            case AttendanceStu:return new AttendanceStuBOImpl();
            case AttendanceTea:return new AttendanceTeaBOImpl();
            case Exam:return new ExamBOImpl();
            default: return null;
        }

    }

}
