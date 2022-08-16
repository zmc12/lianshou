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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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

    @GetMapping(value = "/getInfo")
    public Result getInfo(@RequestHeader("token") String token){
        if(JwtHelper.isExpiration(token)){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        HashMap<String, Object> map = new HashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }


        return Result.ok(map);
    }


    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {


        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String verifiCode = loginForm.getVerifiCode();

        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码为空");
        }

        if (!sessionVerifiCode.equalsIgnoreCase(verifiCode)) {
            return Result.fail().message("验证码错误");
        }
        session.removeAttribute("verifiCode");


        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
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

                try {
                    Student student = studentService.login(loginForm);
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
                try {
                    Teacher teacher = teacherService.login(loginForm);
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
