package com.czxy.jmyp.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 购物车的实体类
 */
@Data
public class CartItem {
    private Integer skuid;
    private Integer spuid;
    @JsonProperty("goods_name")
    private String goodsName;
    private String price;
    private Integer count;//购买数量
    private Boolean checked;
    private String midlogo;
    @JsonProperty("spec_info")
    private String specInfo;

}

