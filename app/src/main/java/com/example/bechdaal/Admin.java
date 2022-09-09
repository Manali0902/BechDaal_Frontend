package com.example.bechdaal;

public class Admin {
    String adminid, adminEmail, adminName, adminPhone, adminPassword;

    public Admin(String adminid, String adminEmail, String adminName, String adminPhone, String adminPassword) {
        this.adminid = adminid;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminPassword = adminPassword;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}