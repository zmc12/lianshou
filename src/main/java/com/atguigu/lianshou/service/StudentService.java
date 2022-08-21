package com.atguigu.lianshou.service;

import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 19:57
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);


    IPage<Student> getStudentByOpr(Page<Student> page, Student student);

    Student selectByPwd(Long userId, String oldPwd);
}
