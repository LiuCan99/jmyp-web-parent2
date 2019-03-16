package com.czxy.jmyp.dao;

import com.czxy.jmyp.pojo.SkuPhoto;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuPhotoMapper extends Mapper<SkuPhoto> {

    @Select("select * from tb_sku_photo where sku_id = #{spuId}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="sku_id",property="skuId"),
            @Result(column="url",property="url")
    })
    public List<SkuPhoto> findSkuPhotoBySkuId(Integer spuId);

}
