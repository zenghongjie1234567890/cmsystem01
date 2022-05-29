package com.zhj.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * emp_vue_protect
 *
 * @author : 曾小杰
 * @date : 2022-03-22 20:50
 **/
// 将该类注入到容器里面
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // name传入redisTemplate 得到bean类
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
