package com.zhj.service.impl;

import com.zhj.entity.SysTeacher;
import com.zhj.mapper.SysTeacherMapper;
import com.zhj.service.ISysTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhj
 * @since 2022-04-11
 */
@Service
public class SysTeacherServiceImpl extends ServiceImpl<SysTeacherMapper, SysTeacher> implements ISysTeacherService {

    @Autowired
    private SysTeacherMapper sysTeacherMapper;

    @Override
    public List<SysTeacher> getStuNameList() {
        List<SysTeacher> list = sysTeacherMapper.selectTeacherName();
        return list;
    }
}
