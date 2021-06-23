package com.yxxt.gradems.resp;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.ScoreAppeal;

public class AppealResp {

    ScoreAppeal appeal;
    Course course;
    String studentName;
    Long studentId;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public ScoreAppeal getAppeal() {
        return appeal;
    }

    public void setAppeal(ScoreAppeal appeal) {
        this.appeal = appeal;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
