package com.czxy.jmyp.dao;

import com.czxy.jmyp.pojo.SpecificationOption;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SpecificationOptionMapper extends Mapper<SpecificationOption> {

    @Select("select * from tb_specification_option where spec_id = #{specificationId}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="spec_id",property="spec_id"),
            @Result(column="option_name",property="option_name"),
    })
    List<SpecificationOption> findSpecificationOptionBySpecificationId( Integer specificationId);

}

