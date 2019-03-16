package com.czxy.jmyp.feignclient;

import com.czxy.jmyp.vo.OneSkuResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 远程调用web-service中的goods方法
 * 查询商品详情
 */
@FeignClient(value="web-service")
@RequestMapping
public interface SkuClient {
    @GetMapping("/goods/{skuid}")
    public ResponseEntity<OneSkuResult> querySkuBySkuid(@PathVariable("skuid") Integer skuid);

}
