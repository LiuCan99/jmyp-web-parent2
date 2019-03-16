package com.czxy.jmyp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/** 查询条件的封装对象
 */
@Data
public class SearchRequest {
    /*
    ?keyword=关键词&catid=分类ID&brand_id=品牌ID
   &spec_list[]=规格列表&min_price=最低价格&max_price=最高价格
   &limit=限制条数&page=当前页&sort_by=排序字段&sort_way=排序方式
   &per_page=每页条数

     */

    private String keyword;                 // 关键字搜索，预留
    @JsonProperty("cat_id")
    private Integer cat_id;                  // 3 级类目
    @JsonProperty("brand_id")
    private Integer brand_id;                // 品牌
    @JsonProperty("spec_list")
    private Map<String,String> specList;    // 规格选项列表
    @JsonProperty("min_price")
    private Double min_price;                //最低价格
    @JsonProperty("max_price")
    private Double max_price;                //最高价格
    private Integer limit;                  //限制条数
    private Integer page;                   //当前页
    @JsonProperty("sort_by")
    private String sort_by;                  //排序字段
    @JsonProperty("sort_way")
    private String sort_way;                 //排序方式 asc desc
    @JsonProperty("per_page")
    private Integer per_page;                //每页条数

}

