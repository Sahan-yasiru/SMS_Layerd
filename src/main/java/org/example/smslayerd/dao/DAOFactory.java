package org.example.smslayerd.dao;

import org.example.smslayerd.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public static DAOFactory getInstance(){
        return daoFactory==null?new DAOFactory():daoFactory;
    }

     public enum DAOTypes{
        User,
         Class,
         Student,
         Subject,
         Teacher,
         TimeTable,
         AttendanceStu,
         AttendanceTea,
         Exam
     }
     public SuperDao getDAO(DAOTypes enumDaoType){
        switch (enumDaoType){
            case User : return new UserDAOImpl();
            case Class:return new ClassDAOImpl();
            case Student:return new StudentDAOImpl();
            case Subject:return new SubjectDAOImpl();
            case Teacher:return new TeacherDAOImpl();
            case TimeTable:return new TimeTableDAOImpl();
            case AttendanceStu:return new AttendStudentDAOImpl();
            case AttendanceTea:return new AttendTeacherDAOImpl();
            case Exam:return new ExamDAOImpl();
            default:return null;
        }

     }
}
