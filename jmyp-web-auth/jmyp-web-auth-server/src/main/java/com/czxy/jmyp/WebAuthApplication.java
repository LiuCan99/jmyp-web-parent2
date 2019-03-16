package com.czxy.jmyp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient          //eureka客户端
@EnableFeignClients             //feign 远程
public class WebAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAuthApplication.class,args);
    }
}
