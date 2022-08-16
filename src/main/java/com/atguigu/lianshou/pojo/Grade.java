package com.atguigu.lianshou.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_grade")
public class Grade {

    private int id;
    private String name;
    private String manager;
    private String email;
    private String telephone;
    private String introducation;

}
