package com.czxy.jmyp.service;

import com.alibaba.fastjson.JSON;
import com.czxy.jmyp.cart.Cart;
import com.czxy.jmyp.cart.CartItem;
import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.feignclient.SkuClient;
import com.czxy.jmyp.mapper.AddressMapper;
import com.czxy.jmyp.mapper.OrderGoodsMapper;
import com.czxy.jmyp.mapper.OrderMapper;
import com.czxy.jmyp.pojo.Address;
import com.czxy.jmyp.pojo.Order;
import com.czxy.jmyp.pojo.OrderGoods;
import com.czxy.jmyp.utils.IdWorker;
import com.czxy.jmyp.vo.OrderRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;

@Service
@Transactional
public class OrderService {
    @Resource
    private IdWorker idWorker;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private SkuClient skuClient;

    public Long createOrder(UserInfo userInfo , OrderRequest orderRequest) {
        //1 生成订单
        Order order = new Order();

        //1.1 设置订单号
        // 生成orderId
        long sn = idWorker.nextId();
        order.setSn(sn+"");

        //1.2 设置用户信息
        order.setUserId(userInfo.getId());

        //1.3 设置地址信息
        // 获得前台传输过来的收货地址和收货人信息，生成订单，保存到数据库
        Address address = addressMapper.selectByPrimaryKey(orderRequest.getAddressId());
        order.setShrName(address.getShrName());
        order.setShrMobile(address.getShrMobile());
        order.setShrProvince(address.getShrProvince());
        order.setShrCity(address.getShrCity());
        order.setShrArea(address.getShrArea());
        order.setShrAddress(address.getShrAddress());
        //1.4 设置状态：创建订单的时候，默认情况是未支付状态
        order.setStatus(0);

        //2 获得购物车：从redis获得当前用户对应的购物车
        String key = "cart" + userInfo.getId();
        String cartJsonStr = stringRedisTemplate.opsForValue().get(key);
        Cart cart = JSON.parseObject( cartJsonStr ,Cart.class );

        //1.5 设置总价格
        order.setTotalPrice( cart.getTotal() );
        //1.6 保存订单
        orderMapper.insert( order );

        //3 保存购物车中已经勾选的商品信息
        // 3.1 遍历购物项
        Iterator<CartItem> it = cart.getData().values().iterator();
        while(it.hasNext()) {
            CartItem cartItem = it.next();
            if(cartItem.getChecked()) {
                // 3.2 将购物车中商品的信息赋值给OrderGoods
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setSn(sn+"");
                orderGoods.setSkuId(cartItem.getSkuid());
                orderGoods.setSpuId(cartItem.getSpuid());
                orderGoods.setNumber(cartItem.getCount());
                orderGoods.setSpecList(cartItem.getSpecInfo());
                orderGoods.setSkuName(cartItem.getGoodsName());
                orderGoods.setLogo(cartItem.getMidlogo());
                orderGoods.setPrice(cartItem.getPrice());
                // 3.3 保存购物车中的商品信息
                orderGoodsMapper.insert(orderGoods);
                // 3.4 购物车中移除该商品
                it.remove();
                // 3.5  远程调用方法，将该商品的数量减少
                skuClient.updateSkuNum(cartItem.getSkuid(),cartItem.getCount());
            }
        }
        //3.6 更新redis购物车
        stringRedisTemplate.opsForValue().set(key , JSON.toJSONString(cart));

        //4 返回sn
        return sn;
    }

}
