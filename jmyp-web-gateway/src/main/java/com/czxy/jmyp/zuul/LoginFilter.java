package com.czxy.jmyp.zuul;

import com.czxy.jmyp.config.FilterProperties;
import com.czxy.jmyp.config.JwtProperties;
import com.czxy.jmyp.util.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liangtong.
 */
@Component
//2.1 加载JWT配置类
@EnableConfigurationProperties({JwtProperties.class , FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    //2.2 注入jwt配置类实例
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private FilterProperties filterProperties;

    @Override
    public String filterType() {        //01.确定拦截位置，如果是访问前拦截（前置：pre）
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {          //02. 多个过滤器执行顺序，数组越小，优先执行
        return 5;
    }

    @Override
    public boolean shouldFilter() {     //03.当前过滤器是否执行，true执行，false不执行
        //3.1 获得用户请求路径
        // 3.1.1 获得上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 3.1.2 获得request
        HttpServletRequest request = currentContext.getRequest();
        // 3.1.2 获得请求路径  , /v1/auth-service/login
        String requestURI = request.getRequestURI();

        //3.2 如果路径是 /auth-service/login ，当前拦截不执行
        for (String path  : filterProperties.getAllowPaths()) {
            //  /v1/auth-service/login  --> ["","v1","auth-service","login"]
            String[] pathArr = requestURI.split("/");
            //只要有一个路径匹配的，过滤器不执行
            if(path.equals("/" + pathArr[3])){

                return false;
            }
        }

        //3.3 其他都执行
        return true;
    }

    @Override
    public Object run() throws ZuulException {  //04. 过滤器核心业务代码

        //1 获得token
        //1.1 获得上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //1.2 获得request对象
        HttpServletRequest request = currentContext.getRequest();
        //1.3 获得指定请求头的值
        String token = request.getHeader("Authorization");


        //2 校验token -- 使用JWT工具类进行解析
        // 2.3 使用工具类，通过公钥获得对应信息
        try {
            JwtUtils.getInfoFromToken( token , jwtProperties.getPublicKey() );
        } catch (Exception e) {
            // 2.4 如果有异常--没有登录（没有权限）
            currentContext.setResponseStatusCode( 403 );        //响应的状态码：403
            currentContext.setSendZuulResponse( false );        //没有响应内容
        }

        // 2.5 如果没有异常，登录了--放行
        return null;
    }
}
