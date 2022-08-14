package com.atguigu.lianshou.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_admin")
public class Admin {

    private Integer id;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;

}
