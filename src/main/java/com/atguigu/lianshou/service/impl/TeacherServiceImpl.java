package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.TeacherMapper;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Teacher;
import com.atguigu.lianshou.service.TeacherService;
import com.atguigu.lianshou.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:04
 */
@Service("teacherServiceImpl")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUserName());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return baseMapper.selectOne(queryWrapper);
    }
}
