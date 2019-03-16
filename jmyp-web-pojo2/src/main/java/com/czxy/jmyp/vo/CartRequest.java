package com.czxy.jmyp.vo;

import lombok.Data;

/**
 * 封装购物车中商品的对象
 * 如果skuid在购物车列表中已存在就只+count
 * 不存在就将该商品添加到购物车列表中
 */
@Data
public class CartRequest {

    private Integer skuid ; //:"SKUID",
    private Integer count; //:"购买数量"
    private Boolean checkde;// 是否选中

}
