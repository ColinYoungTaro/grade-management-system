package com.yxxt.gradems.req;

public class StudentQueryCourseReq {
    private Long userId;

    private Integer currentYear;

    private Integer currentTerm;

    private String courseUid;

    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
    }

    public String getCourseUid() {
        return courseUid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public Integer getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public void setCurrentTerm(Integer currentTerm) {
        this.currentTerm = currentTerm;
    }
}
