package com.czxy.jmyp.controller;

import com.czxy.jmyp.util.PayHelper;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.PayRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class PayController {

    @Resource
    private PayHelper payHelper;

    @PostMapping("/pay")
    public ResponseEntity<BaseResult> pay(@RequestBody PayRequest payRequest){
        String payUrl =payHelper.createPayUrl(payRequest.getSn());
        return ResponseEntity.ok(new BaseResult(0,"成功").append("wxurl", payUrl));
    }

}
