package com.yxxt.gradems.resp;

import java.util.Date;

public class SchoolStudentUserQueryResp {
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

    ///////////////////////////Student//////////////////////////

    private Integer majorId;

    private Integer grade;

    private Integer status;

    private Integer adminClassId;

    private Integer enterYear;

    public Long getRowId() { return rowId; }

    public void setRowId(Long rowId) { this.rowId = rowId; }

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

    ////////////////////////Student///////////////////////////////////

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAdminClassId() {
        return adminClassId;
    }

    public void setAdminClassId(Integer adminClassId) {
        this.adminClassId = adminClassId;
    }

    public Integer getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(Integer enterYear) {
        this.enterYear = enterYear;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", userName=").append(userName);
        sb.append(", passwordEncode=").append(passwordEncode);
        sb.append(", email=").append(email);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", majorId=").append(majorId);
        sb.append(", grade=").append(grade);
        sb.append(", status=").append(status);
        sb.append(", adminClassId=").append(adminClassId);
        sb.append(", enterYear=").append(enterYear);
        sb.append("]");
        return sb.toString();
    }
}