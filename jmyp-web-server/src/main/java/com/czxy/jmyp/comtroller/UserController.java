package com.czxy.jmyp.comtroller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.czxy.jmyp.pojo.User;
import com.czxy.jmyp.service.UserService;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.utils.SmsUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private HttpSession session;

    //redis
    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("/regist")
    public ResponseEntity<Object> regist(@RequestBody User user){
        userService.saveUser(user);
        return  ResponseEntity.ok(new BaseResult(0,"注册成功"));

    }

    /**
     * 发送短信
     */
    @PostMapping("/sms")
    public  ResponseEntity<BaseResult> sendSms(@RequestBody User user){
        try {
            //发送短信
            //1.生产验证码
            String code = RandomStringUtils.randomNumeric(4);
            System.out.println("手机验证码为：" + code);

            //2 并存放到reids中 , key:手机号 ， value：验证码 , 1小时
            redisTemplate.opsForValue().set( user.getMobile() , code , 1 , TimeUnit.HOURS);

            //3.发送短信
            SendSmsResponse sms = SmsUtil.sendSms(user.getMobile(), "客户", code, "---", "---");
            System.out.println(sms.getMessage()+"==============");
            if("OK".equalsIgnoreCase(sms.getCode())){
                return  ResponseEntity.ok(new BaseResult(0, "发送成功"));
            }else {
                return  ResponseEntity.ok(new BaseResult(1, sms.getMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.ok(new BaseResult(1, "发送失败"));
        }

    }

    /**
     * 通过手机号和密码进行查询
     * @param mobile
     * @param password
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("mobile") String mobile , @RequestParam("password") String password){
        //1 通过手机号查询用户
        User user = this.userService.findByMobile( mobile );
        //2 判断密码是否正确
        if(user == null || !user.getPassword().equals(password)){
            //密码不对
            return ResponseEntity.ok( null );
        }
        //3 正确
        return ResponseEntity.ok( user );
    }


}
