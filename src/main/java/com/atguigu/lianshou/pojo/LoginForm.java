package com.atguigu.lianshou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
