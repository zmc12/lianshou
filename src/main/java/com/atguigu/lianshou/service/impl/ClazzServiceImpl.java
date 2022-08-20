package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.ClazzMapper;
import com.atguigu.lianshou.pojo.Clazz;
import com.atguigu.lianshou.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:00
 */
@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String name = clazz.getName();
        String gradeName = clazz.getGradeName();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("grade_name",gradeName);
        }

        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);

    }

    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
