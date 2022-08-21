package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.TeacherMapper;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Teacher;
import com.atguigu.lianshou.service.TeacherService;
import com.atguigu.lianshou.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:04
 */
@Service("teacherServiceImpl")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(teacher.getName())){
            queryWrapper.like("name",teacher.getName());
        }
        if(!StringUtils.isEmpty(teacher.getClazzName())){
            queryWrapper.eq("clazz_name",teacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Teacher selectByPwd(Long userId, String oldPwd) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId.intValue());
        queryWrapper.eq("password",oldPwd);
        return this.getOne(queryWrapper);
    }
}
