package com.czxy.jmyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "tb_sku")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sku {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //库存量
    @Column(name="stock")
    private Integer stock;

    /**
     * 商品分类对象
     */
    @Column(name="spu_id")
    private Integer spuId;
    @Transient
    private Spu spu;

    //sku名字
    @Column(name="sku_name")
    private String skuName;

    @Column(name="images")
    private String images;
    @Column(name="price")
    private String price;

    //1:1|2:6|6:22
    @Column(name="spec_info_id_list")
    private String specInfoIdList;
    //规格列表码,格式：{"机身颜色":"白色","内存":"3GB","机身存储":"16GB"}
    @Column(name="spec_info_id_txt")
    private String specInfoIdTxt;


}
