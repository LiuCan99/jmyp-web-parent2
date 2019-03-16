package com.czxy.jmyp.dao;

import com.czxy.jmyp.pojo.SkuComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuCommentMapper extends Mapper<SkuComment> {

    /**
     * 根据skuid查询商品评论数量
     * @param skuId
     * @return
     */
    @Select("select count(*) from tb_sku_comment where sku_id = #{skuId}")
    public Integer findNumBySkuId( @Param("skuId") Integer skuId);

    /**
     * 根据spuid获取所有评论的数量
     * @param spuId
     * @return
     */
    @Select("select count(*) from tb_sku_comment where spu_id = #{spuId}")
    public Integer findNumBySpuId(Integer spuId);

    /**
     *根据spuid获取评论
     * @param spuid
     * @return
     */
    @Select("select * from tb_sku_comment where spu_id = #{spuid}")
    public List<SkuComment> findCommentsBySpuid(Integer spuid);


    /**
     *根据supid查询指定的评论级别
     * @param spuid
     * @param ratio 评论级别
     * @return
     */

    @Select("select count(*) from tb_sku_comment where spu_id = #{spuid} and ratio = #{ratio}")
    public Integer findCommentCountByRatio(@Param("spuid")Integer spuid,@Param("ratio")Integer ratio);

    /**
     * 根据skuid查询商品平均评论星星
     */
    @Select("select avg(star) from tb_sku_comment where sku_id = #{skuId}")
    public Integer findAvgStarBySkuId( @Param("skuId") Integer skuId);


//    @Select("select count(*) from tb_sku_comment where spu_id = #{spuid}")
//    public Integer findTotalCommentBySpuid(Integer spuid);




}
