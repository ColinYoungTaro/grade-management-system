package com.yxxt.gradems.domain;

public class Major {
    private Integer majorId;

    private Long rowId;

    private String majorName;

    private Byte schoolPeriod;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Byte getSchoolPeriod() {
        return schoolPeriod;
    }

    public void setSchoolPeriod(Byte schoolPeriod) {
        this.schoolPeriod = schoolPeriod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", majorId=").append(majorId);
        sb.append(", rowId=").append(rowId);
        sb.append(", majorName=").append(majorName);
        sb.append(", schoolPeriod=").append(schoolPeriod);
        sb.append("]");
        return sb.toString();
    }
}