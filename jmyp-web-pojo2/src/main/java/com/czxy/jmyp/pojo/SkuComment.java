package com.czxy.jmyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "tb_sku_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

/**
 * 商品评价实体类
 */
public class SkuComment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="user_id")
    private Integer userId;
    @Transient
    private User user;

    /**
     *对应spu（商品分类）对象
     */
    @Column(name="spu_id")
    private Integer spuId;
    @Transient
    private Spu spu;

    /**
     * 对应sku（商品信息）对象
     */
    @Column(name="sku_id")
    private Integer skuId;
    @Transient
    private Spu sku;


    @Column(name="ratio")
    private String ratio;

    @Column(name="spec_list")
    private String specList;


    @Column(name="content")
    private String content;
    @Column(name="star")
    private Integer star;
    @Column(name="isshow")
    private String isShow;

    @Column(name="sn")
    private String sn;

}

