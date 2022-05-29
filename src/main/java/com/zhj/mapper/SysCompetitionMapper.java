package com.zhj.mapper;

import com.zhj.entity.SysCompetition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhj.redis.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-04-14
 */
@Mapper
public interface SysCompetitionMapper extends BaseMapper<SysCompetition> {

}
