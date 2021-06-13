package com.yxxt.gradems.resp;

public class StudentQueryCourseResp {
    //////////////////////////course////////////////////////
    private String courseUid;

    private String courseName;

    private Float credit;

    private Float schoolHour;

    private Short courseType;

    private Integer year;

    private Integer term;
    /////////////////////course_schedule////////////////////

    private Long teacherId;

    private Integer classIndex;

    /////////////////////self_defined//////////////////////
    private String stringCourseType;

    private String departmentName;

    private Integer selectCourseStatus;

    private String stringStatus;

    public String getStringStatus() {
        return stringStatus;
    }

    public void setStringStatus(String stringStatus) {
        this.stringStatus = stringStatus;
    }

    private String teacherName;

    private String courseTerm;

    public void setCourseTerm() {
        if(this.getTerm()==1){
            this.courseTerm = "春";
        }else if(this.getTerm()==2){
            this.courseTerm = "夏";
        }else if(this.getTerm()==3){
            this.courseTerm = "秋";
        }else if(this.getTerm()==4){
            this.courseTerm = "冬";
        }
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getSelectCourseStatus() {
        return selectCourseStatus;
    }

    public void setSelectCourseStatus(Integer selectCourseStatus) {
        this.selectCourseStatus = selectCourseStatus;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setStringCourseType(String stringCourseType) {
        this.stringCourseType = stringCourseType;
    }

    public String getStringCourseType() {
        return stringCourseType;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }


    public Integer getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(Integer classIndex) {
        this.classIndex = classIndex;
    }

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
