package com.zhj.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.*;
import com.zhj.service.ISysTeacherService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-04-11
 */
@RestController
@RequestMapping("teacher")
public class SysTeacherController {

    @Autowired
    private ISysTeacherService iSysTeacherService;

    @PostMapping("savePicture")
    // 图片参数名一定要和前端的一样 @RequestParam("photo")该注解可以用别名，解决前后端命名参数名字不一样
    public String saveEmp(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        // 头像保存: 保存客户端上传的图像到服务端
        String uuid = IdUtil.fastSimpleUUID();
        String newFileName = uuid + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File("E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\teacher_picture", newFileName));
        return "/teacher_picture/"+newFileName;
    }

    // 保存老师信息
    @PostMapping("saveTeacher")
    public Map<String,Object> saveTea(@RequestBody SysTeacher sysTeacher){
        HashMap<String, Object> map = new HashMap<>();
        try {
            System.out.println(sysTeacher);
            iSysTeacherService.save(sysTeacher);
            map.put("code",true);
            map.put("msg","指导老师信息添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",false);
            map.put("msg","添加指定老师信息失败");
        }
        return map;
    }

    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String teacherCollege,
                                        @RequestParam(defaultValue = "") String teacherName,
                                        HttpServletRequest request) {
        IPage<SysTeacher> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SysTeacher> wrapper = new QueryWrapper<>();
        wrapper.like("teacher_name", teacherName);
        wrapper.like("teacher_college", teacherCollege);
        List<SysTeacher> list = iSysTeacherService.list(wrapper);
        // 将查询的结构放入servletContext作用域，便于下一个请求域提取值
        request.getServletContext().setAttribute("list01",list);
        IPage<SysTeacher> iPage = iSysTeacherService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", iPage);
        return map;
    }

    // 删除单个用户
    @GetMapping("delTeacher/{id}")
    public Map<String, Object> delUser(@PathVariable("id") String id) {
        System.out.println(id);
        HashMap<String, Object> map = new HashMap<>();
        boolean b = iSysTeacherService.removeById(id);
        return map;
    }

    // 更新信息
    @PostMapping("updateTeacher")
    public Map<String, Object> UpdateTeacher(@RequestBody SysTeacher sysTeacher) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            boolean b1 = iSysTeacherService.updateById(sysTeacher);
            map.put("msg", b1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }
    // 导出数据
    @GetMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 查询出所有数据
        List<SysStu> list1 = (List<SysStu>) request.getServletContext().getAttribute("list01");
        // 在内存中操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //// 可以自定义表头的别名
        //writer.addHeaderAlias("username","用户名");
        // 一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list1, true);
        String fileName = "老师信息表.xlsx";// 文件名
        // 设置浏览器响应格式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    @GetMapping("getAllName")
    public List<SysTeacher> getAllName(){
        List<SysTeacher> list = iSysTeacherService.getStuNameList();
        return list;
    }
}
