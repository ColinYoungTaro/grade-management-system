package com.yxxt.gradems.resp;

import java.util.Date;

public class SchoolTeacherUserQueryResp {
    /////////////////////SchoolUser////////////////////////////////

    private Long rowId;

    private Long userId;

    private Integer roleId;

    private Integer departmentId;

    private String userName;

    private String passwordEncode;

    private String email;

    private String phoneNumber;

    private Boolean gender;

    private Date birthday;

    public Long getRowId() {
        return rowId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "SchoolTeacherUserQueryResp{" +
                "rowId=" + rowId +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", departmentId=" + departmentId +
                ", userName='" + userName + '\'' +
                ", passwordEncode='" + passwordEncode + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                '}';
    }
}
