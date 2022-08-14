package com.atguigu.lianshou.pojo;

import lombok.Data;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:43
 */
@Data
public class LoginForm {
    private String userName;
    private String password;
    private String verifiCode;
    private Integer userType;
}
