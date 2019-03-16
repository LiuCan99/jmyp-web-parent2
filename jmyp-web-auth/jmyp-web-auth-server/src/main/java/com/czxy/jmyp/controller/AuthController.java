package com.czxy.jmyp.controller;

import com.czxy.jmyp.pojo.User;
import com.czxy.jmyp.service.AuthService;
import com.czxy.jmyp.vo.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResult> login(@RequestBody User user){
        //1登录操作--获得token
        String token = this.authService.login( user.getMobile() ,user.getPassword() );
        //2 有token，返回
        if(StringUtils.isNotBlank( token)){
            BaseResult baseResult = new BaseResult(0 , "登录成功").append("token", token);
            return ResponseEntity.ok( baseResult );
        }
        //3 没有token，失败
        return ResponseEntity.ok( new BaseResult(1 , "登录失败"));
    }

}
