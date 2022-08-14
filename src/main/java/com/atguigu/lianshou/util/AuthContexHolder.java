package com.atguigu.lianshou.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 18:52
 */
public class AuthContexHolder {

    public static Long getUserIdToken(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        return  userId;
    }


    public static String getUserName(HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = JwtHelper.getUserName(token);
        return userName;
    }

}
