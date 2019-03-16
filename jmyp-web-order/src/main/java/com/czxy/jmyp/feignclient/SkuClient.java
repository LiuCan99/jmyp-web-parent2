package com.czxy.jmyp.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("web-service")
@RequestMapping
public interface SkuClient {

    /**
     * 远程调用 SkuController中的 updateSkuNum 更新购物车中商品数量的方法
     * @param skuid  商品id
     * @param count  数量
     * @return
     */
    @PutMapping("/good/{skuid}")
    public ResponseEntity<String> updateSkuNum(@PathVariable("skuid") Integer skuid , @RequestParam("count") Integer count);


}
