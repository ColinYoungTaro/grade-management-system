package com.yxxt.gradems.resp;

import com.yxxt.gradems.config.VarConfig;

public class StudentScoreQueryResp {
    private String courseUid;

    private String courseName;

    private Float credit;

    private Float schoolHour;

    private Short courseType;

    private Integer year;

    private Integer term;

    private Long studentId;

    private Integer classIndex;

    private Integer score;

    private Double gpa;

    public Double getGpa() {
        return gpa;
    }

    public void setGpa() {
        Integer score = 0;
        score = this.getScore();
        if(score >= 95){
            this.gpa = VarConfig.FULL_GPA;
        }else if(score >= 92){
            this.gpa = VarConfig.SECOND_GPA;
        }else if(score >= 89){
            this.gpa = VarConfig.THIRD_GPA;
        }else if(score >= 85){
            this.gpa = VarConfig.FOURTH_GPA;
        }else if(score >= 80){
            this.gpa = VarConfig.FIFTH_GPA;
        }else if(score >= 70){
            this.gpa = VarConfig.SIXTH_GPA;
        }else if(score >= 60){
            this.gpa = VarConfig.SEVENTH_GPA;
        }else{
            this.gpa = VarConfig.EIGHTH_GPA;
        }
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(Integer classIndex) {
        this.classIndex = classIndex;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    @Override
    public String toString() {
        return "StudentScoreQueryResp{" +
                "courseUid='" + courseUid + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", schoolHour=" + schoolHour +
                ", courseType=" + courseType +
                ", year=" + year +
                ", term=" + term +
                ", studentId=" + studentId +
                ", classIndex=" + classIndex +
                ", score=" + score +
                '}';
    }
}
