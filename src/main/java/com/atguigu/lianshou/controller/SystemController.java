package com.atguigu.lianshou.controller;

import com.atguigu.lianshou.pojo.Admin;
import com.atguigu.lianshou.pojo.LoginForm;
import com.atguigu.lianshou.pojo.Student;
import com.atguigu.lianshou.pojo.Teacher;
import com.atguigu.lianshou.service.AdminService;
import com.atguigu.lianshou.service.StudentService;
import com.atguigu.lianshou.service.TeacherService;
import com.atguigu.lianshou.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

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

    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable("oldPwd")String oldPwd,@PathVariable("newPwd")String newPwd,@RequestHeader("token")String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("登陆超时，请重新登陆");
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                Admin admin = adminService.selectByPwd(userId,oldPwd);
                if(admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("密码输入错误");
                }
                break;
            case 2:
                Student student = studentService.selectByPwd(userId,oldPwd);
                if(student != null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("密码输入错误");
                }
                break;
            case 3:
                Teacher teacher = teacherService.selectByPwd(userId,oldPwd);
                if(teacher != null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("密码输入错误");
                }
                break;
        }

        return Result.ok();
    }


    @PostMapping(value = "/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile")MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFilename = uuid+originalFilename.substring(i);

        String portPath = "D:/idea/project/project/lianshou/target/classes/public/upload/"+newFilename;
        try {
            multipartFile.transferTo(new File(portPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "upload/"+newFilename;
        return Result.ok(path);
    }

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
                        Long id = ((Integer)admin.getId()).longValue();
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
                        Long id = ((Integer)student.getId()).longValue();                        String token = JwtHelper.createToken(id, 2);
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
                        Long id = ((Integer)teacher.getId()).longValue();
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
