package com.czxy.jmyp.service;

import com.alibaba.fastjson.JSON;
import com.czxy.jmyp.cart.Cart;
import com.czxy.jmyp.cart.CartItem;
import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.feignclient.SkuClient;
import com.czxy.jmyp.vo.CartRequest;
import com.czxy.jmyp.vo.OneSkuResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private SkuClient skuClient;

    @Resource
    private HttpServletRequest req;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 添加商品到购物车
     * 用户登录的时候，可以一个一个添加商品进购物车
     * @param userInfo
     * @param cartRequest
     */
    public void addCart(UserInfo userInfo , CartRequest cartRequest){

        //1 获得购物车
        Cart cart;
        String key = "cart" + userInfo.getId();
        String cartStr = redisTemplate.opsForValue().get(key);
        // 处理是否有购物车，没有创建，有转换(jsonStr --> java对象 )
        if(cartStr != null){
            //如果有，将json字符串转换购物车对象
            cart = JSON.parseObject( cartStr , Cart.class);

        } else {
            //如果没有创建一个
            cart = new Cart();
        }

        //2 保存商品
        // 2.1 确定购物买商品
        ResponseEntity<OneSkuResult> entity = skuClient.querySkuBySkuid(cartRequest.getSkuid());
        OneSkuResult oneSkuResult = entity.getBody();

        // * 将OneSkuResult 转换成 CartItem
        CartItem cartItem = new CartItem();
        cartItem.setSkuid( oneSkuResult.getSkuid() );
        cartItem.setSpuid( oneSkuResult.getSpuid() );
        cartItem.setGoodsName( oneSkuResult.getGoodsName() );
        cartItem.setPrice( oneSkuResult.getPrice() );
        cartItem.setCount( cartRequest.getCount() );        //购买数量，用户传递的
        cartItem.setChecked(true);
        cartItem.setMidlogo( oneSkuResult.getLogo().get("biglogo"));
        cartItem.setSpecInfo( JSON.toJSONString( oneSkuResult.getSpecInfo() ) );   //将对象转换json字符串

        // 2.2 添加到购物车
        cart.addCart( cartItem );

        //3 保存购物车
        redisTemplate.opsForValue().set( key , JSON.toJSONString(cart) );
    }


    /**
     * 查询购物车列表
     * @param userInfo
     * @return
     */
    public Cart queryCartList(UserInfo userInfo) {
        String key = "cart" + userInfo.getId().toString();
        // 获取hash操作对象
        String cartString = this.redisTemplate.opsForValue().get(key);

        // 2 获得购物车，如果没有创建一个
        return JSON.parseObject(cartString, Cart.class);
    }


    /**
     * 更新购物车
     * @param userInfo
     * @param cartRequest
     */
    public  void  updateCart( UserInfo userInfo,CartRequest cartRequest){
        //1获得购物车
        String key="cart"+userInfo.getId();
        String catStr=redisTemplate.opsForValue().get(key);
           //处理是否有购物车，没有创建，有转换（jsonStr——>java对象）
        Cart cart=JSON.parseObject(catStr,Cart.class);
        if(cart==null){
            throw  new  RuntimeException("购物车不存在");
        }
        //2 更新
        cart.updateCart(cartRequest.getSkuid(),cartRequest.getCount(),cartRequest.getCheckde());

        //3 保存购物车
        redisTemplate.opsForValue().set(key,JSON.toJSONString(cart));
    }


    /**
     * 删除购物车中的商品
     * @param userInfo
     * @param skuid
     */
    public void deleteCart(UserInfo userInfo, Integer skuid) {
        //1 获得购物车
        String key = "cart" + userInfo.getId();
        String cartStr = redisTemplate.opsForValue().get(key);
        // 处理是否有购物车，没有创建，有转换(jsonStr --> java对象 )
        Cart cart = JSON.parseObject( cartStr , Cart.class);
        if(cart == null) {
            throw new RuntimeException("购物车不存在");
        }

        //2 更新
        cart.deleteCart(skuid);

        //3 保存购物车
        redisTemplate.opsForValue().set( key , JSON.toJSONString(cart) );
    }


}
