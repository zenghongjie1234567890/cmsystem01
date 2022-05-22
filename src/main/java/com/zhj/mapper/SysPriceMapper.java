package com.zhj.mapper;

import com.zhj.entity.SysPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-05-13
 */
@Mapper
public interface SysPriceMapper extends BaseMapper<SysPrice> {
     String getCompName(@Param("compid") Integer compid, @Param("teamid") Integer front);
     List<System> getPrice(@Param("compid") Integer compid);
}
