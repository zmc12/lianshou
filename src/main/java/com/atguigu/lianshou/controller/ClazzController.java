package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Clazz;
import com.atguigu.lianshou.service.ClazzService;
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
@RequestMapping(value = "/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> list = clazzService.getClazzs();
        return Result.ok(list);
    }


    @DeleteMapping(value = "/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping(value = "/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @GetMapping(value = "/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable("pageNo")Integer pageNo, @PathVariable("pageSize")Integer pageSize, Clazz clazz){

        Page<Clazz> page = new Page<Clazz>(pageNo,pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page,clazz);

        return Result.ok(iPage);
    }
}
