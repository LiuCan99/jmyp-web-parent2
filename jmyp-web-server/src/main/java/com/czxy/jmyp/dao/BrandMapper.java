package com.czxy.jmyp.dao;

import com.czxy.jmyp.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {
    @Select("select b.* from tb_brand b ,tb_category_brand cb " +
            "  where b.id = cb.brand_id and cb.category_id = #{categoryId}")
    @Results({
            @Result(property = "brandName",column = "brand_name")
    })
    public List<Brand> findBrandByCatid(@Param("categoryId") Integer categoryId);

}
