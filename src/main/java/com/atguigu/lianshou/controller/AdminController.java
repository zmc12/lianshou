package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Admin;
import com.atguigu.lianshou.service.AdminService;
import com.atguigu.lianshou.util.MD5;
import com.atguigu.lianshou.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/14 14:05
 */

@RestController
@RequestMapping(value = "/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @DeleteMapping("deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        Integer id = admin.getId();
        if(id == 0 || id == null){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo")Integer pageNo,@PathVariable("pageSize")Integer pageSize,String adminName){
        Page<Admin> page = new Page<>(pageNo,pageSize);
        IPage<Admin> iPage = adminService.getAdminByOpr(page,adminName);
        return Result.ok(iPage);
    }
}
