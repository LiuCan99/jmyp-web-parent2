package com.czxy.jmyp.controller;

import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.service.OrderService;
import com.czxy.jmyp.util.JwtUtils;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.OrderRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@EnableConfigurationProperties(JwtProperties.class)
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private HttpServletRequest request;

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) {

        //1校验token
        UserInfo userInfo;
        try {
            String token = request.getHeader("Authorization");
            //解析token获得用户信息
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }


        Long sn = this.orderService.createOrder(userInfo , orderRequest);

        return ResponseEntity.ok(new BaseResult(0,"成功").append("sn", sn+""));
    }


}

