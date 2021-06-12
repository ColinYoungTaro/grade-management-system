package com.yxxt.gradems.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq {

    // 请求的页数
    @NotNull(message = "页码不为空")
    private int page;

    // 一页的大小
    @NotNull(message = "每页条数不为空")
    @Max(value = 100, message = "每页条数不能超过100")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageReq{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
