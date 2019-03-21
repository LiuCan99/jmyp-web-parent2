package com.czxy.jmyp.controller;


import com.czxy.jmyp.service.SkuSearchService;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class SkuSearchController {

    @Resource
    private SkuSearchService skuSearchService;

    /**
     * 商品查询功能
     * @param searchRequest 查询条件的封装对象
     * @return
     */
    @PostMapping("/search")
    public ResponseEntity<BaseResult> search( @RequestBody SearchRequest searchRequest){
       // BaseResult baseResult = this.skuSearchService.search( searchRequest );
        //1 查询（调用service中的方法）
        BaseResult search = skuSearchService.search(searchRequest);
        //2 封装，并返回
        return ResponseEntity.ok( search );

    }


}


