package com.zhj.service;

import com.zhj.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhj
 * @since 2022-04-11
 */
public interface ISysTeacherService extends IService<SysTeacher> {
    List<SysTeacher> getStuNameList();
}
