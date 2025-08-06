package org.example.smslayerd.view.tdm;

public class AdminTM {
    private String adminID;
    private String userName;
    private String password;
    private String adminType;

    public AdminTM(String adminID, String userName, String password, String adminType) {
        this.adminID = adminID;
        this.userName = userName;
        this.password = password;
        this.adminType = adminType;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }
}
