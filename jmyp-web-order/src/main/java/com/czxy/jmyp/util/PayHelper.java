package com.czxy.jmyp.util;

import com.czxy.jmyp.config.PayProperties;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableConfigurationProperties(PayProperties.class)
public class PayHelper {

    private WXPay wxPay;

    @Bean
    public WXPay wxPay(PayProperties payProperties){
        if(wxPay == null){
            wxPay = new WXPay(payProperties);
        }
        return wxPay;
    }

    private static final Logger logger = LoggerFactory.getLogger(PayHelper.class);

    public PayHelper() {

    }

    public PayHelper(PayProperties payProperties) {
        // 真实开发时
        wxPay = new WXPay(payProperties);
        // 测试时
        // wxPay = new WXPay(payProperties, WXPayConstants.SignType.MD5, true);
    }

    public String createPayUrl(Long sn) {
        String key = "pay.url." + sn;

        try {
            Map<String, String> data = new HashMap<>();
            // 商品描述
            data.put("body", "商城测试");
            // 订单号
            data.put("out_trade_no", sn.toString());
            //货币
            data.put("fee_type", "CNY");
            //金额，单位是分
            data.put("total_fee", "1");
            //调用微信支付的终端IP（商城的IP）
            data.put("spbill_create_ip", "127.0.0.1");
            //回调地址
            data.put("notify_url", "http://test.jingxi.com/wxpay/notify");
            // 交易类型为扫码支付
            data.put("trade_type", "NATIVE");
            //商品id,使用假数据
            data.put("product_id", "1234567");

            Map<String, String> result = this.wxPay.unifiedOrder(data);
            if ("SUCCESS".equals(result.get("return_code"))) {
                if("SUCCESS".equals(result.get("result_code"))){
                    String url = result.get("code_url");

                    return url;
                } else {
                    logger.error("创建预交易订单失败，错误信息：{}", result.get("err_code_des"));
                    return null;
                }
            } else {
                logger.error("创建预交易订单失败，错误信息：{}", result.get("return_msg"));
                return null;
            }
        } catch (Exception e) {
            logger.error("创建预交易订单异常", e);
            return null;
        }
    }

    /**
     * 查询订单状态
     * 交易状态参考：
     SUCCESS—支付成功
     REFUND—转入退款
     NOTPAY—未支付
     CLOSED—已关闭
     REVOKED—已撤销（付款码支付）
     USERPAYING--用户支付中（付款码支付）
     PAYERROR--支付失败(其他原因，如银行返回失败)
     * @param sn
     * @return
     */
    public PayState queryOrder(Long sn) {
        Map<String, String> data = new HashMap<>();
        // 订单号
        data.put("out_trade_no", sn.toString());
        try {
            Map<String, String> result = this.wxPay.orderQuery(data);
            if (result == null) {
                // 未查询到结果，认为是未付款
                return PayState.NOT_PAY;
            }
            String state = result.get("trade_state");
            if ("SUCCESS".equals(state)) {

                return PayState.SUCCESS;
            } else if (StringUtils.equals("USERPAYING", state) || StringUtils.equals("NOTPAY", state)) {
                // 未付款或正在付款，都认为是未付款
                return PayState.NOT_PAY;
            } else {
                // 其它状态认为是付款失败
                return PayState.FAIL;
            }
        } catch (Exception e) {
            logger.error("查询订单状态异常", e);
            return PayState.NOT_PAY;
        }
    }
}

