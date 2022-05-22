package com.zhj.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.*;
import com.zhj.mapper.SysPriceMapper;
import com.zhj.service.ISysPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-05-13
 */
@RestController
@RequestMapping("price")
public class SysPriceController {

    @Autowired
    private ISysPriceService service;

    // 导出数据
    @GetMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 查询出所有数据
        List<SysTeam> list1 =(List<SysTeam>) request.getServletContext().getAttribute("price");
        SysTeam sysTeam = list1.get(1);
        String name = service.getCompName(sysTeam.getTeamCompid(), sysTeam.getTeamId());
        // copy  system对象的一些属性
        List<copy> list = new ArrayList<>();
        for (SysTeam sysTeam1:list1){
            copy copy = new copy();
            copy.setTeamId(sysTeam1.getTeamId());
            copy.setTeamName(sysTeam1.getTeamName());
            copy.setTeamWorks(sysTeam1.getTeamWorks());
            copy.setTeamNums(sysTeam1.getTeamNums());
            copy.setTeamGrade(sysTeam1.getTeamGrade());
            copy.setTeamPrice(sysTeam1.getTeamPrice());
            copy.setTeamTeaid(sysTeam1.getTeamTeaid());
            list.add(copy);
        }
        // 在内存中操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //// 可以自定义表头的别名
        writer.addHeaderAlias("teamId","队伍编号");
        writer.addHeaderAlias("teamName","队伍名称");
        writer.addHeaderAlias("teamWorks","参赛作品");
        writer.addHeaderAlias("teamTeaid","指定老师");
        writer.addHeaderAlias("teamNums","团队人数");
        writer.addHeaderAlias("teamGrade","分数");
        writer.addHeaderAlias("teamPrice","奖项");
        writer.merge(6, name+"获奖队伍名单");
        // 一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list, true);
        String fileName = name+"----获奖队伍名单.xlsx";// 文件名
        // 设置浏览器响应格式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    // 添加信息
    @PostMapping("addPrice")
    public Map<String,Object> add(@RequestBody SysPrice sysPrice, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            request.getServletContext().setAttribute("priceid",sysPrice.getCompid());
            LocalDate localDate = LocalDate.now();
            sysPrice.setFileData(localDate);
            service.save(sysPrice);
            map.put("code",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",false);
        }
        return map;
    }

    // 附件上传
    @PostMapping("upload")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        int num = (Integer) request.getServletContext().getAttribute("priceid");
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        String jname=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        File file1 = new File("C:\\Users\\Admin\\Desktop\\cmsystem\\src\\main\\resources\\static\\file", uuid + StrUtil.DOT + type);
        file.transferTo(file1);
        String url = "http://localhost:8055/price/" + uuid + StrUtil.DOT + type;
        UpdateWrapper<SysPrice> wrapper = new UpdateWrapper<>();
        wrapper.eq("compid",num);
        wrapper.set("fileUrl",url);
        wrapper.set("fileName",jname);
        service.update(wrapper);
    }

    // 下载文件
    @GetMapping("{fileuuid}")
    public void download(@PathVariable String fileuuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File file1 = new File("C:\\Users\\Admin\\Desktop\\cmsystem\\src\\main\\resources\\static\\file", fileuuid);
        SysPrice one = service.getOne(new QueryWrapper<SysPrice>().like("fileUrl", fileuuid));
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(one.getFileName()+".xlsx", "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(file1));
        os.flush();
        os.close();
    }

    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "") Integer id) {
        IPage<SysPrice> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SysPrice> wrapper = new QueryWrapper<>();
        if (id!=null){
            wrapper.eq("compid",id);
        }
        List<SysPrice> list = service.list(wrapper);
        IPage<SysPrice> iPage = service.page(page,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", iPage);
        return map;
    }

    // 删除
    @GetMapping("del")
    public void delComp(Integer compId){
        service.removeById(compId);
    }

    // 查找一个
    @GetMapping("getone")
    public Map<String,Object> getone( @RequestParam Integer compid){
        HashMap<String, Object> map = new HashMap<>();
        SysPrice id = service.getById(compid);
        map.put("file",id);
        List<copy> list = new ArrayList<>();
        List<System> list1 = service.getPrice(compid);
        map.put("price",list1);
        return map;
    }

}

