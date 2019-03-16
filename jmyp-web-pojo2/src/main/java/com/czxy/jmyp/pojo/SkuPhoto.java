package com.czxy.jmyp.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品图片实体类
 */
@Table(name = "tb_sku_photo")
@Data
public class SkuPhoto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //外键sku商品信息对象
    @Column(name="sku_id")
    private Integer skuId;
    @Transient
    private Sku sku;

    @Column(name="url")
    private String url;

}
