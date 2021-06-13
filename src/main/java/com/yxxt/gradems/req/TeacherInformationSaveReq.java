package com.yxxt.gradems.req;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TeacherInformationSaveReq {
    /////////////////////SchoolUser////////////////////////////////

    @NotNull(message = "用户id不能为空")
    private Long userId;

    private Integer roleId;

    private Integer departmentId;

    private String userName;

    private String passwordEncode;

    private String email;

    private String phoneNumber;

    private Boolean gender;

    private Date birthday;

    /////////////////////////Information///////////////////////
    private String departmentName;

    private String stringGender;

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setStringGender(String stringGender) {
        this.stringGender = stringGender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getStringGender() {
        return stringGender;
    }
}
