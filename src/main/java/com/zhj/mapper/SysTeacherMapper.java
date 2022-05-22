package com.zhj.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.zhj.entity.SysTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-04-11
 */
@Mapper
public interface SysTeacherMapper extends BaseMapper<SysTeacher> {
    List<SysTeacher> selectTeacherName();
}
