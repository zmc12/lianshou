package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Grade;
import com.atguigu.lianshou.service.GradeService;
import com.atguigu.lianshou.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/14 14:09
 */

@RestController
@RequestMapping(value = "/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @DeleteMapping(value = "/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids){

        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping(value = "/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @GetMapping(value = "/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable Integer pageNo,@PathVariable Integer pageSize,String gradeName){

        Page<Grade> page = new Page<>(pageNo, pageSize);
        IPage<Grade> iPage = gradeService.getGradeByOpr(page,gradeName);

        return Result.ok(iPage);

    }



}
