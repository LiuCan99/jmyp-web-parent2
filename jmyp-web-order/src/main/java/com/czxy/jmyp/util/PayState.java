package com.czxy.jmyp.util;

/**
 * Created by liangtong.
 * 自定义支付状态，微信支持多种状态，此处统一三种：未支付、支付成功、支付失败
 */
public enum  PayState {
    NOT_PAY(0),SUCCESS(1),FAIL(2);

    PayState(int value) {
        this.value = value;
    }

    int value;

    public int getValue() {
        return value;
    }
}
