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
        registry.addResourceHandler("/www/wwwroot/static/teacher_picture/**" , "/www/wwwroot/static/teacher_picture/**")
                .addResourceLocations("file:/www/wwwroot/static/teacher_picture/",
                        "file:/www/wwwroot/static/stu_picture/");
    }
}
