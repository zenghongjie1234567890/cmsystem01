package com.zhj.redis;
import com.zhj.config.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * emp_vue_protect
 *
 * @author : 曾小杰
 * @date : 2022-03-21 22:57
 **/
@Slf4j
public class RedisCache implements Cache {

    private String id;

    public RedisCache(String id) {
        log.info("当前的缓存id:[{}]",id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override // 放入redis缓存
    public void putObject(Object key, Object value) {
        log.info("放入缓存key:[{}] ,放入缓存的value:[{}]",key,value);
        getRedisTemplate().opsForHash().put(id,key.toString(),value);
    }

    @Override// 从redis缓存中获取
    public Object getObject(Object key) {
        log.info("获取缓存的key:[{}]",key.toString());
        return getRedisTemplate().opsForHash().get(id,key.toString());
    }

    @Override// 删除指定缓存信息
    public Object removeObject(Object key) {
        return null;
    }

    @Override// 清除信息
    public void clear() {
        log.info("清除所有缓存信息....");
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        return getRedisTemplate().opsForHash().size(id).intValue();
    }

    // 直接创建一个方法得到redisTemplate
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate =(RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
