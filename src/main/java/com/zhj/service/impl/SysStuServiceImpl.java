package com.zhj.service.impl;

import com.zhj.entity.SysStu;
import com.zhj.mapper.SysStuMapper;
import com.zhj.service.ISysStuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhj
 * @since 2022-04-09
 */
@Service
public class SysStuServiceImpl extends ServiceImpl<SysStuMapper, SysStu> implements ISysStuService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public void put(Object a, Object b) {
        redisTemplate.opsForValue().set(a,b);
    }
}
