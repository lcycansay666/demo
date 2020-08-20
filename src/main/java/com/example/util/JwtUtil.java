/**
 * @Auther: PC-8
 * @Date: 2020/8/20 15:38
 * @Description:
 */
package com.example.util;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
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
        Map<String, Object> claims = new HashMap<>();

        claims.put("aa",1);
        claims.put("bb",2);

        //指定签名的时候使用的签名算法
        io.jsonwebtoken.SignatureAlgorithm signatureAlgorithm = io.jsonwebtoken.SignatureAlgorithm.HS256;


        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())//设置签发日期
                .signWith(signatureAlgorithm, SIGNING_KEY);//设置签名 使用HS256算法，并设置SecretKey(字符串)
                //构建 并返回一个字符串
        System.out.println(builder.compact());


        //得到DefaultJwtParser
        Claims claims1 = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(SIGNING_KEY)
                //设置需要解析的token
                .parseClaimsJws(builder.compact()).getBody();

        System.out.println(JSON.toJSONString(claims1));
    }

}
