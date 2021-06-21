package com.yxxt.gradems.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    USER_ID_ERROR("用户ID不存在"),
    COURSE_ID_ERROR("课程ID不存在"),
    USER_STATUS_ERROR("学籍状态不支持选课"),
    KEY_NOT_EXIST("查询关键字不存在"),
    DB_OPERATION_ERROR("数据库内部错误"),
    PERMISSION_DENIED("权限不允许"),
    INVALID_OPERATION("非法操作"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}