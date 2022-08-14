package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.GradeMapper;
import com.atguigu.lianshou.pojo.Grade;
import com.atguigu.lianshou.service.GradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:01
 */
@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
}
