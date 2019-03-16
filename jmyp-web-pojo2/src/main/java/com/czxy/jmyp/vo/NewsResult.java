package com.czxy.jmyp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class NewsResult extends  BaseResult{
    private Integer total;
    private Object data;

    public NewsResult(Integer errno, String errmsg, Integer total, Object data) {
        super(errno, errmsg);
        this.total = total;
        this.data = data;
    }
}
