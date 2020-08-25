/**
 * @Auther: PC-8
 * @Date: 2020/8/20 15:38
 * @Description:
 */
package com.example.util;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lcy
 * @Date: 2020/8/20 15:38
 * @Description:
 */
public class JwtUtil {

    public static void main(String[] args) {


        String SIGNING_KEY = "sdasdawqdfxcv";

        long now = System.currentTimeMillis();
        long exp = now + 1000 * 3;//30秒过期

        Map<String, Object> claims = new HashMap<>();

        claims.put("aa", 1);
        claims.put("bb", 2);

        //指定签名的时候使用的签名算法
        io.jsonwebtoken.SignatureAlgorithm signatureAlgorithm = io.jsonwebtoken.SignatureAlgorithm.HS256;


        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())//设置签发日期
                .setExpiration(new Date(exp))
                .signWith(signatureAlgorithm, SIGNING_KEY);//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        System.out.println(builder.compact());

        try {
            //得到DefaultJwtParser
            Claims claims1 = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(SIGNING_KEY)
                    //设置需要解析的token
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJhYSI6MSwiYmIiOjIsImV4cCI6MTU5NzkxMDAyOCwiaWF0IjoxNTk3OTEwMDI1fQ.bqPSJ4EOEwfc-bMHuKXfAVSn7WOOtsPnsPKvsmppMI0").getBody();

            System.out.println(JSON.toJSONString(claims1));
        } catch (ExpiredJwtException e) {
            System.out.println("过期");
        }

    }

}
