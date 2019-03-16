package com.czxy.jmyp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

/**
 * 地址信息
 */
@Table(name = "tb_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    //用户ID
    @Column(name="user_id")
    private Integer userId;
    //收货人姓名
    @Column(name="shr_name")
    private String shrName;
    //收货人手机
    @Column(name="shr_mobile")
    private String shrMobile;
    //收货人省份
    @Column(name="shr_province")
    private String shrProvince;
    //收货人城市
    @Column(name="shr_city")
    private String shrCity;
    //收货人地区
    @Column(name="shr_area")
    private String shrArea;
    //收货人详情地址
    @Column(name="shr_address")
    private String shrAddress;
    //1:默认;0:不是
    @Column(name="isdefault")
    private Integer isdefault;

}
