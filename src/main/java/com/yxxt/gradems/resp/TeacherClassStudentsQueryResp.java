package com.yxxt.gradems.resp;

public class TeacherClassStudentsQueryResp {
    private Long userId;

    private String userName;

    private Long studentId;

    private String courseUid;

    private Integer classIndex;

    private Integer score;

    private Boolean scoredStatus;

    public Boolean getScoredStatus() {
        return scoredStatus;
    }

    public void setScoredStatus(Boolean scoredStatus) {
        this.scoredStatus = scoredStatus;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
