package org.example.smslayerd.entity;

public class Teacher {
    private String teacherID;
    private String subjectID;
    private String name;
    private String classId;
    private Integer gradeAssign;

    public Teacher(String teacherID, String subjectID, String name, String classId, int gradeAssign) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
        this.name = name;
        this.classId = classId;
        this.gradeAssign = gradeAssign;
    }
    public Teacher() {}

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getGradeAssign() {
        return gradeAssign;
    }

    public void setGradeAssign(int gradeAssign) {
        this.gradeAssign = gradeAssign;
    }
}
