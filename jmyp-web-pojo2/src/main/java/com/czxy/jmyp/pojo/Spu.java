package com.czxy.jmyp.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_spu")
@Data
public class Spu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    //spu名字
    @Column(name="spu_name")
    private String spuName;
    //spu副名称
    @Column(name="spu_subname")
    private String spuSubname;
    //商品logo
    @Column(name="logo")
    private String logo;
    //分类1Id
    @Column(name="cat1_id")
    private Integer cat1Id;
    //分类2ID
    @Column(name="cat2_id")
    private Integer cat2Id;
    //分类3Id
    @Column(name="cat3_id")
    private Integer cat3Id;

    /**
     * 品牌对象
     */
    @Column(name="brand_id")
    private Integer brandId;
    @Transient
    private Brand brand;


    //审核时间
    @Column(name="check_time")
    private String checkTime;
    //审核状态 审核状态，0：未审核，1：已通过，2：未通过
    @Column(name="check_status")
    private String checkStatus;
    //价格
    @Column(name="price")
    private String price;
    //是否上架
    @Column(name="is_on_sale")
    private Integer isOnSale;
    //上架时间
    @Column(name="on_sale_time")
    private Date onSaleTime;
    //删除时间
    @Column(name="deleted_at")
    private String deletedAt;

    @Column(name="weight")
    private String weight;

    //商品描述
    @Column(name="description")
    private String description;
    //规格与包装
    @Column(name="packages")
    private String packages;
    //售后保障
    @Column(name="aftersale")
    private String aftersale;
    //规格列表，json串
    @Column(name="spec_list")
    private String specList;

    @Column(name="created_at")
    private String createdAt;
    @Column(name="updated_at")
    private String updatedAt;


}
