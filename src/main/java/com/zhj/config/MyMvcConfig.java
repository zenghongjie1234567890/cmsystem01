package com.zhj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    // 配置虚拟路径映射访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射图片保存地址
        registry.addResourceHandler("/teacher_picture/**" , "/stu_picture/**")
                .addResourceLocations("file:E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\teacher_picture\\",
                        "file:E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\stu_picture\\");
    }
}
