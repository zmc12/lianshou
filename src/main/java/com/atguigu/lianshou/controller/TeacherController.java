package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Teacher;
import com.atguigu.lianshou.service.TeacherService;
import com.atguigu.lianshou.util.MD5;
import com.atguigu.lianshou.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/14 14:10
 */
@RestController
@RequestMapping(value = "/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable("pageNo")Integer pageNo,@PathVariable Integer pageSize,Teacher teacher){
        Page<Teacher> page = new Page<>(pageNo,pageSize);
        IPage<Teacher> iPage = teacherService.getTeacherByOpr(page,teacher);
        return Result.ok(iPage);
    }

    @PostMapping(value = "/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        Integer id = teacher.getId();
        if(id == 0 || id == null){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping(value = "/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
