package com.czxy.jmyp.cart;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车对象
 */

@Data
public class Cart {

    private Map<Integer , CartItem > data = new HashMap<>();
    private Double totle;

    /**
     * 添加商品到购物车
     * @param cartItem
     */
    public void addCart(CartItem cartItem) {
        CartItem temp = data.get(cartItem.getSkuid());
        if(temp == null) {
            data.put( cartItem.getSkuid() , cartItem);
        } else {
            temp.setCount( cartItem.getCount() + temp.getCount() );
        }
    }

    /**
     * 更新购物车中的商品
     * @param skuid
     * @param count
     * @param checked
     */
    public void updateCart(Integer skuid, Integer count , Boolean checked) {
        CartItem temp = data.get(skuid);
        if(temp != null) {
            temp.setCount( count );
            temp.setChecked(checked);
        }
    }

    /**
     * 删除购物车中的商品
     * @param skuid
     */
    public void deleteCart(Integer skuid) {
        data.remove( skuid );
    }

    /**
     * 计算商品总价
     * @return
     */

    public Double getTotal() {
        double sum = 0.0;
        for (CartItem cartItem : data.values()) {
            //只统计勾选的价格
            if(cartItem.getChecked()){
                Integer price=Integer.parseInt(cartItem.getPrice()+"");
                sum += (  price* cartItem.getCount());
            }
        }
        return sum;
    }



}
