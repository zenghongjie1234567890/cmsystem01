package com.zhj.mapper;

import com.zhj.entity.SysStu;
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
 * @since 2022-04-09
 */
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
@Mapper
public interface SysStuMapper extends BaseMapper<SysStu> {

}
