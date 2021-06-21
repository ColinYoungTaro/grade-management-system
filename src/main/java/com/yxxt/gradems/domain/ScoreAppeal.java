package com.yxxt.gradems.domain;

public class ScoreAppeal {
    private Long rowId;

    private Long studentId;

    private String courseUid;

    private Integer classIndex;

    private Short originalScore;

    private Short alteredScore;

    private Integer status;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
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

    public Short getOriginalScore() {
        return originalScore;
    }

    public void setOriginalScore(Short originalScore) {
        this.originalScore = originalScore;
    }

    public Short getAlteredScore() {
        return alteredScore;
    }

    public void setAlteredScore(Short alteredScore) {
        this.alteredScore = alteredScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rowId=").append(rowId);
        sb.append(", studentId=").append(studentId);
        sb.append(", courseUid=").append(courseUid);
        sb.append(", classIndex=").append(classIndex);
        sb.append(", originalScore=").append(originalScore);
        sb.append(", alteredScore=").append(alteredScore);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}