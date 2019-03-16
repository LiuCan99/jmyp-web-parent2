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

    //@GetMapping("/search")
    @PostMapping("/search")
    public ResponseEntity<BaseResult> search( @RequestBody SearchRequest searchRequest){

        System.out.println(searchRequest);

        //1 查询
       // BaseResult baseResult = this.skuSearchService.search( searchRequest );
        BaseResult search = skuSearchService.search(searchRequest);

        //2 封装
        return ResponseEntity.ok( search );

    }


}


