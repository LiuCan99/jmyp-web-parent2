package com.czxy.jmyp.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/*接收页面请求参数*/
@Data
@NoArgsConstructor
public class PageRequest {
    private Integer limit;// 限制条数
    private Integer offset;// 起始索引
    private Integer page; // 当前页
    private Integer perPage;// 每页条数
    private String sortBy; // 排序字段
    private String sortWay; //排序方式  asc  升序   desc  降序

}
