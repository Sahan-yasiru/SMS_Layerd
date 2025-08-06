package org.example.smslayerd.view.tdm;

public class SubjectTM {
    private String subjectID;
    private String name;

    public SubjectTM(String subjectID, String name){
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
