package com.yxxt.gradems.req;

public class SchoolTeacherUserQueryReq extends PageReq{
    private Long userId;

    private Integer roleId;

    private Integer departmentId;

    private String userName;

    private Boolean gender;

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

    public Boolean getGender() {
        return gender;
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

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SchoolTeacherUserQuery{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                ", departmentId=" + departmentId +
                ", userName='" + userName + '\'' +
                ", gender=" + gender +
                '}';
    }


}
