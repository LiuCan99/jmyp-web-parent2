package com.czxy.jmyp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;


@Data
public class ReturnSku {

    private Integer id;
    @Column(name = "goods_name")
    @JsonProperty("goods_name")

    private String goodsName;
    private String price;
    private String midlogo;
    @Column(name = "comment_count")
    @JsonProperty("comment_count")
    private Integer commentCount;

}
