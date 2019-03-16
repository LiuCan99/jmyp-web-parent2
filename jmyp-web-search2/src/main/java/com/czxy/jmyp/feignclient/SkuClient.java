package com.czxy.jmyp.feignclient;


import com.czxy.jmyp.vo.SearchSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@FeignClient(value="web-service")
@RequestMapping
public interface SkuClient {

    @GetMapping("/esData")
    public ResponseEntity<List<SearchSku>> findAll();

}
