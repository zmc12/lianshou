package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Student;
import com.atguigu.lianshou.service.StudentService;
import com.atguigu.lianshou.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/14 14:10
 */
@RestController
@RequestMapping(value = "/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo")Integer pageNo, @PathVariable("pageSize")Integer pageSize, Student student){
        Page<Student> page = new Page<>(pageNo,pageSize);
        IPage<Student> iPage = studentService.getStudentByOpr(page,student);
        return Result.ok(iPage);
    }
}
