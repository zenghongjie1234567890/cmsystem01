package com.zhj.mapper;
import java.util.List;

import com.zhj.redis.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import com.zhj.entity.SysTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
@Mapper
public interface SysTeamMapper extends BaseMapper<SysTeam> {
    List<Integer> getTeam(Integer compId);

    List<SysTeam> getByTeamCompidOrderByTeamGradeDesc(@Param("teamCompid") Integer teamCompid,@Param("front") Integer front,@Param("later") Integer later);

}
