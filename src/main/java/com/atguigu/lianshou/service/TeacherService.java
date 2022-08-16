package com.atguigu.lianshou.service;

import com.atguigu.lianshou.pojo.Admin;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:57
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);
}
