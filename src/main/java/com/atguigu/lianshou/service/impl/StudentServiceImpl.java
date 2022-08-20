package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.StudentMapper;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Student;
import com.atguigu.lianshou.service.StudentService;
import com.atguigu.lianshou.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:02
 */
@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String name = student.getName();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        String clazzName = student.getClazzName();
        if(!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name",clazzName);
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page,queryWrapper);
    }

}
