package com.yxxt.gradems.resp;

public class CommonResp<T> {

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseDto{");
        sb.append("success=").append(success);
        sb.append(", message='").append(message).append('\'');
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
    public static<T> CommonResp<T> error(String msg){
        CommonResp<T> resp = new CommonResp<>();
        resp.setContent(null);
        resp.setMessage(msg);
        resp.setSuccess(false);
        return resp;
    }
    public static<T> CommonResp<T> error(String msg,T content){
        CommonResp<T> resp = new CommonResp<>();
        resp.setContent(content);
        resp.setMessage(msg);
        resp.setSuccess(false);
        return resp;
    }

    public static<T> CommonResp<T> success(String msg,T content){
        CommonResp<T> resp = new CommonResp<>();
        resp.setContent(content);
        resp.setMessage(msg);
        resp.setSuccess(true);
        return resp;
    }
}