package com.atguigu.lianshou.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_student")
public class Student {

    private int id;
    private Integer sno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private Long telephone;
    private String address;
    private String introducation;
    private String portraitPath;
    private String clazzName;
}
