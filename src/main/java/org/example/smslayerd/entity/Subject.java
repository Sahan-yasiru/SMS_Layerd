package org.example.smslayerd.entity;

public class Subject {
    private String subjectID;
    private String name;

    public Subject(String subjectID, String name){
        this.name=name;
        this.subjectID=subjectID;
    }
    public void setSubjectID(String subjectID){
        this.subjectID=subjectID;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }


    public String getSubjectID(){
        return this.subjectID;
    }
}
