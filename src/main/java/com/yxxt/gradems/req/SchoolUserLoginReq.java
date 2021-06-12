package com.yxxt.gradems.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SchoolUserLoginReq {
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotEmpty(message = "密码不能为空")
    private String passwordEncode;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", passwordEncode=").append(passwordEncode);
        sb.append("]");
        return sb.toString();
    }
}