package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.GradeMapper;
import com.atguigu.lianshou.pojo.Grade;
import com.atguigu.lianshou.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:01
 */
@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(gradeName)){
            gradeQueryWrapper.like("name",gradeName);
        }
        gradeQueryWrapper.orderByDesc("id");
        Page<Grade> page1 = baseMapper.selectPage(page, gradeQueryWrapper);

        return page1;
    }
}
