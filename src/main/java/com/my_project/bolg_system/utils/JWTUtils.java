package com.my_project.bolg_system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtils {

    public static String secret = "dVnsmy+SIX6pNptQdeclDSJ26EMSPEIhvZYKBTTug4k=";
    public static long expiration = 24 * 60 * 60 * 1000;//一天

    private static SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

    /**
     * 生成token
     * @param claim
     * @return
     */
    public static String genJwt(Map<String, Object> claim) {
        String jwt = Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    /**
     * 验证令牌
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        if (!StringUtils.hasLength(jwt)) {
            return null;
        }
        //创建解析器, 设置签名密钥
        JwtParserBuilder jwtParserBuilder =
                Jwts.parserBuilder().setSigningKey(secretKey);
        Claims claims = null;
        try {
            //解析token
            claims = jwtParserBuilder.build().parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            //签名验证失败
            log.error("解析令牌错误,jwt:{}", jwt);
        }
        return claims;
    }

    /**
     * 从token中获取用户ID
     */
    public static Integer getUserIdFromToken(String jwtToken) {
        Claims claims = JWTUtils.parseJWT(jwtToken);
        if (claims != null) {
            Map<String, Object> userInfo = new HashMap<>(claims);
            return (Integer) userInfo.get("id");
        }
        return null;
    }


}

