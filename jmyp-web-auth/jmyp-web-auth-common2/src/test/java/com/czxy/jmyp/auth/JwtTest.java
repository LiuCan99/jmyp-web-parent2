package com.czxy.jmyp.auth;


import com.czxy.jmyp.entity.UserInfo;
import com.czxy.jmyp.util.JwtUtils;
import com.czxy.jmyp.util.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "E:\\ras\\ras.pub";

    private static final String priKeyPath = "E:\\ras\\ras.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU0NDY5NjU4OH0.SAP9OgADKQRJpmNdksEvx4u9rnUjRJaxPCzFU8FcVujwE2o3Oes9Ket513uUHWggNek5BsixNG5jsPI75xbIFEie8SO7pQOHU6wVFfoOphuUhAVYCV88cZDVyDIgpXrPVjhSF_y8GTMA-x0qAQpqDQJEz_18w9zFOIVsVAmm-Pc";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
       System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}

