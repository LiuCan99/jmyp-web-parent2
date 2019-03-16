package com.czxy.jmyp.dao;

import com.czxy.jmyp.pojo.Impression;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ImpressionMapper extends Mapper<Impression> {

    /**
     * 根据spuid查询印象（评论）
     * @param spuid
     * @return
     */
    @Select("select * from tb_impression where spu_id = #{spuid}")
    public List<Impression> findImpressionsBySpuid(Integer spuid);

}
