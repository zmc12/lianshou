package com.atguigu.lianshou.service.impl;

import com.atguigu.lianshou.mapper.ClazzMapper;
import com.atguigu.lianshou.pojo.Clazz;
import com.atguigu.lianshou.service.ClazzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ZhangMinCong
 * @Date: 2022/8/12 20:00
 */
@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
}
