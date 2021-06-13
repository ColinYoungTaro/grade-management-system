package com.yxxt.gradems.req;

public class CourseQueryReq extends PageReq{
    private String courseUid;

    private String courseName;

    private Short courseType;

    private Integer year;

    private Integer term;

    public String getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

}
