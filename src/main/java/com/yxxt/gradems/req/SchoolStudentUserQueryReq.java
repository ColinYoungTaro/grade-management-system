package com.yxxt.gradems.req;

public class SchoolStudentUserQueryReq extends PageReq{
    private Long userId;

    private Integer roleId;

    private Integer departmentId;

    private String userName;

    private Boolean gender;

    ////////////////////////////////////

    private Integer majorId;

    private Integer grade;

    private Integer status;

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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getMajorId() { return majorId; }

    public void setMajorId(Integer majorId) { this.majorId = majorId; }

    public Integer getGrade() { return grade; }

    public void setGrade(Integer grade) { this.grade = grade; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

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
        sb.append(", gender=").append(gender);
        sb.append("]");
        return sb.toString();
    }
}