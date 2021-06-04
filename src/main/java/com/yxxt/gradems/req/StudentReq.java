package com.yxxt.gradems.req;

public class StudentReq extends PageReq{
    private Long userId;

    private Integer rowId;

    private Integer majorId;

    private Integer grade;

    private Integer status;

    private Integer adminClassId;

    private Integer enterYear;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

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
        sb.append(", rowId=").append(rowId);
        sb.append(", majorId=").append(majorId);
        sb.append(", grade=").append(grade);
        sb.append(", status=").append(status);
        sb.append(", adminClassId=").append(adminClassId);
        sb.append(", enterYear=").append(enterYear);
        sb.append("]");
        return sb.toString();
    }
}