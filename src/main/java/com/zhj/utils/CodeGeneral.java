package com.zhj.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis_plus
 *
 * @author : 曾小杰
 * @date : 2022-03-26 13:50
 **/
public class CodeGeneral {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/competition_management_system?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("zhj") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Admin\\Desktop\\cmsystem\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com") // 设置父包名
                            .moduleName("zhj") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\Admin\\Desktop\\cmsystem\\src\\main\\resources\\Mapper"));
                    // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_price");// 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker
                .execute();
    }
}
