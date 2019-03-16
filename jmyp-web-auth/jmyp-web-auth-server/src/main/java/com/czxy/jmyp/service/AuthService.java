package com.czxy.jmyp.service;


import com.czxy.jmyp.client.UserClient;
import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.pojo.User;
import com.czxy.jmyp.util.JwtUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liangtong.
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Resource
    private UserClient userClient;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 登录操作
     * @param mobile  手机号
     * @param password 密码
     * @return  token值
     */
    public String login(String mobile , String password){
        try {
            //1 登录--查询
            User user = userClient.queryUser( mobile ,password ).getBody();
            //2 如果不为空，生产token
            if(user != null){
                return JwtUtils.generateToken(new UserInfo( user.getId() ,user.getName() ) , jwtProperties.getPrivateKey() ,  jwtProperties.getExpire() );
            }
            //3 如果为空
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;        //没有登录成功
    }


}
