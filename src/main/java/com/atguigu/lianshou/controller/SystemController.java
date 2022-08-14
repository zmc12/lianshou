package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Admin;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Student;
import com.atguigu.lianshou.pojo.Teacher;
import com.atguigu.lianshou.service.AdminService;
import com.atguigu.lianshou.service.StudentService;
import com.atguigu.lianshou.service.TeacherService;
import com.atguigu.lianshou.util.CreateVerifiCodeImage;
import com.atguigu.lianshou.util.JwtHelper;
import com.atguigu.lianshou.util.Result;
import com.atguigu.lianshou.util.ResultCodeEnum;
import com.sun.deploy.net.HttpResponse;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/14 14:11
 */

@RestController
@RequestMapping(value = "/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String verifiCode = loginForm.getVerifiCode();

        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            Result.fail("验证码为空");
        }

        if (!sessionVerifiCode.equalsIgnoreCase(verifiCode)) {
            Result.fail("验证码错误");
        }
        session.removeAttribute("verifiCode");


        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                Admin admin = adminService.login(loginForm);
                try {
                    if(null != admin){
                        Long id = admin.getId().longValue();
                        String token = JwtHelper.createToken(id, 1);
                        map.put("token",token);

                    }else {
                        throw new RuntimeException("账号或密码错误");
                    }

                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                Student student = studentService.login(loginForm);
                try {
                    if(null != student){
                        Long id = student.getId().longValue();
                        String token = JwtHelper.createToken(id, 2);
                        map.put("token",token);

                    }else {
                        throw new RuntimeException("账号或密码错误");
                    }

                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                Teacher teacher = teacherService.login(loginForm);
                try {
                    if(null != teacher){
                        Long id = teacher.getId().longValue();
                        String token = JwtHelper.createToken(id, 3);
                        map.put("token",token);

                    }else {
                        throw new RuntimeException("账号或密码错误");
                    }

                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

        }
        return Result.fail().message("不存在此账号");
    }


    @GetMapping(value = "/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {

        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();

        String code = new String(CreateVerifiCodeImage.getVerifiCode());


        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", code);
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
