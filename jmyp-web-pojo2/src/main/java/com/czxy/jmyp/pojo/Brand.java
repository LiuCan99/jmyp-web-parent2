package com.czxy.jmyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 品牌管理
 */
@Table(name = "tb_brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

/*
*  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `logo` varchar(200) DEFAULT '' COMMENT '品牌图片地址',
* */
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="brand_name")
    private String brandName;
    @Column(name="logo")
    private String logo;
}

