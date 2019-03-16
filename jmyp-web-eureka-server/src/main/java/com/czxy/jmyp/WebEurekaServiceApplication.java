package com.czxy.jmyp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication    //spring boot
@EnableEurekaServer      //注册中心
public class WebEurekaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebEurekaServiceApplication.class,args);
    }
}
