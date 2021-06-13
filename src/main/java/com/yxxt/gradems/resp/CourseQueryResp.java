package com.yxxt.gradems.resp;

import org.springframework.util.ObjectUtils;

public class CourseQueryResp {
    private String courseUid;

    private Long rowId;

    private String courseName;

    private Float credit;

    private Float schoolHour;

    private Short courseType;

    private Integer year;

    private Integer term;

    /////////////////////////////
    private String stringCourseType;

    private String stringTerm;

    public String getStringCourseType() {
        return stringCourseType;
    }

    public String getStringTerm() {
        return stringTerm;
    }

    public void setStringCourseType(String stringCourseType) {
        this.stringCourseType = stringCourseType;
    }

    public void setStringCourseType() {
        if(!ObjectUtils.isEmpty(this.courseType)){
            if(this.courseType==0){
                setStringCourseType("通识课");
            }else if(this.courseType==1){
                setStringCourseType("专业课");
            }
        }
    }

    public void setStringTerm(String stringTerm) {
        this.stringTerm = stringTerm;
    }

    public void setStringTerm() {
        if(!ObjectUtils.isEmpty(this.term)){
            if(this.term==1){
                setStringTerm("春");
            }else if(this.term==2){
                setStringTerm("夏");
            }else if(this.term==3){
                setStringTerm("秋");
            }else if(this.term==4){
                setStringTerm("冬");
            }
        }
    }


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

}
