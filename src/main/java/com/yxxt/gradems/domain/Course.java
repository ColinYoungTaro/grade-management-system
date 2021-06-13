package com.yxxt.gradems.domain;

public class Course {
    private String courseUid;

    private Long rowId;

    private String courseName;

    private Float credit;

    private Float schoolHour;

    private Short courseType;

    private Integer year;

    private Integer term;

    public String getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Float getSchoolHour() {
        return schoolHour;
    }

    public void setSchoolHour(Float schoolHour) {
        this.schoolHour = schoolHour;
    }

    public Short getCourseType() {
        return courseType;
    }

    public void setCourseType(Short courseType) {
        this.courseType = courseType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseUid=").append(courseUid);
        sb.append(", rowId=").append(rowId);
        sb.append(", courseName=").append(courseName);
        sb.append(", credit=").append(credit);
        sb.append(", schoolHour=").append(schoolHour);
        sb.append(", courseType=").append(courseType);
        sb.append(", year=").append(year);
        sb.append(", term=").append(term);
        sb.append("]");
        return sb.toString();
    }
}