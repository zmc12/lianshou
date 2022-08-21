package com.atguigu.lianshou.service;

import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:57
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher);

    Teacher selectByPwd(Long userId, String oldPwd);
}
