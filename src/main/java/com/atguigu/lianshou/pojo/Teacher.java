package com.atguigu.lianshou.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_teacher")
public class Teacher {

    private Integer id;
    private Integer tno;
    private String name;
    private String gender;
    private String password;
    private String email;
    private Long telephone;
    private String address;
    private String portraitPath;
    private String clazzName;

}
