package com.my_project.bolg_system.utils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public class GetFromHead {
    public static Integer getLogUserId(HttpServletRequest request){
        String user_token = request.getHeader("user_token");
        Claims claims = JWTUtils.parseJWT(user_token);
        Integer log_userId = (Integer) claims.get("log_userId");
        return log_userId;
    }
}
