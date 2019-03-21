package com.czxy.jmyp.controller;

import com.czxy.jmyp.cart.Cart;
import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.service.CartService;
import com.czxy.jmyp.util.JwtUtils;
import com.czxy.jmyp.vo.BaseResult;
import com.czxy.jmyp.vo.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
@EnableConfigurationProperties({JwtProperties.class})
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private HttpServletRequest req;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 添加商品到购物车
     * @param cartRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<BaseResult> addCart(@RequestBody CartRequest cartRequest){

        //1校验token
        UserInfo userInfo;
        try {
            //以请求头的方式获得token
            String token = req.getHeader("Authorization");
            System.out.println("=======================================");
            //通过公钥对token进行校验
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            //没有token则是未登录状态
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }

        //token校验成功，调用addCart方法进行商品添加到购物车
        this.cartService.addCart(userInfo , cartRequest);
        return ResponseEntity.ok( new BaseResult(0 , "成功") );
    }

    /**
     * 查询购物车列表
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> queryCartList() {
        //1校验token
        UserInfo userInfo;
        try {
            String token = req.getHeader("Authorization");
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }

        Cart cart = this.cartService.queryCartList(userInfo);

        return ResponseEntity.ok( new BaseResult( 0 , "成功").append("data" ,cart.getData().values()));

    }

    /**
     * 修改购物车中的商品数据
     */
    @PutMapping
    public ResponseEntity<BaseResult>  updateCart(@RequestBody CartRequest cartRequest){
        //1校验token
        UserInfo userInfo;
        try {
            String token = req.getHeader("Authorization");
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }

        try {
            this.cartService.updateCart(userInfo ,cartRequest);

            return ResponseEntity.ok( new BaseResult( 0 , "成功"));
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , e.getMessage()) );
        }

    }

    /**
     * 删除购物车中的商品
     * @param skuid
     * @return
     */
    @DeleteMapping("/{skuid}")
    public ResponseEntity<BaseResult> deleteCart(@PathVariable("skuid") Integer skuid) {
        //1校验token
        UserInfo userInfo;
        try {
            String token = req.getHeader("Authorization");
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , "失败，没有登录") );
        }

        try {
            this.cartService.deleteCart(userInfo ,skuid);

            return ResponseEntity.ok( new BaseResult( 0 , "成功"));
        } catch (Exception e) {
            return ResponseEntity.ok( new BaseResult(1 , e.getMessage()) );
        }
    }




}
