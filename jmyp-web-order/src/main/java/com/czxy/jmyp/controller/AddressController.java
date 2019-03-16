package com.czxy.jmyp.controller;

import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.pojo.Address;
import com.czxy.jmyp.service.AddressService;
import com.czxy.jmyp.util.JwtUtils;
import com.czxy.jmyp.vo.BaseResult;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;



@RestController
@RequestMapping
@EnableConfigurationProperties(JwtProperties.class)
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private HttpServletRequest request;

    @GetMapping("/address")
    public ResponseEntity<BaseResult> queryAddress(){

        //1校验token
        UserInfo userInfo;
        try {
            String token = request.getHeader("Authorization");
            //获得请求头中的用户信息（token）
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }

        //2 查询
        List<Address> list = this.addressService.findAllByUserId( userInfo.getId() );

        return  ResponseEntity.ok( new BaseResult(0 ,"成功").append("data" , list));
    }

}

