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
import com.zhj.entity.SysFile;
import com.zhj.entity.SysStu;
import com.zhj.entity.SysTeam;
import com.zhj.service.ISysTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-04-27
 */
@RestController
@RequestMapping("team")
public class SysTeamController {

    @Autowired
    private ISysTeamService service;

    // 添加队伍信息
    @PostMapping("addTeam")
    public Map<String,Object> addTeam(@RequestBody SysTeam sysTeam, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(sysTeam);
        try {
            service.save(sysTeam);
            request.getServletContext().setAttribute("teamid",sysTeam.getTeamId());
            map.put("code",true);
            map.put("msg","报名成功！！");
            map.put("id",sysTeam.getTeamId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",false);
            map.put("msg","报名失败！！");
        }
        return map;
    }

    // 附件上传
    @PostMapping("upload")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        int num = (Integer) request.getServletContext().getAttribute("teamid");
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        File file1 = new File("E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\file", uuid + StrUtil.DOT + type);
        file.transferTo(file1);
        String url = "http://localhost:8055/team/" + uuid + StrUtil.DOT + type;
        UpdateWrapper<SysTeam> wrapper = new UpdateWrapper<>();
        wrapper.eq("team_id",num);
        wrapper.set("team_file",url);
        wrapper.set("team_fname",filename);
        service.update(wrapper);
    }

    // 参数队伍分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "") Integer id,
                                        @RequestParam(required = false,defaultValue = "") Integer teamId,
                                        HttpServletRequest request) {
        IPage<SysTeam> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SysTeam> wrapper = new QueryWrapper<>();
        if (id!=null){
            wrapper.eq("team_compid",id);
        }
        if (teamId!=null){
            wrapper.eq("team_id",teamId);
        }
        List<SysTeam> list = service.list(wrapper);
        request.getServletContext().setAttribute("teamList",list);
        IPage<SysTeam> iPage = service.page(page,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", iPage);
        return map;
    }

    // 下载文件
    @GetMapping("{fileuuid}")
    public void download(@PathVariable String fileuuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File file1 = new File("E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\file", fileuuid);
        SysTeam one = service.getOne(new QueryWrapper<SysTeam>().like("team_file", fileuuid));
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(one.getTeamFname(), "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(file1));
        os.flush();
        os.close();
    }

    // 批量删除
    @PostMapping("delmore")
    public void delUser(@RequestBody List<Integer> ids) {
        boolean b = service.removeByIds(ids);
    }

    // 删除单个
    @GetMapping("delstu/{id}")
    public void delUser(@PathVariable("id") String id) {
        boolean b = service.removeById(id);
    }

    // 导出数据
    @GetMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 查询出所有数据
        List<SysTeam> list =(List<SysTeam>) request.getServletContext().getAttribute("teamList");
        // 在内存中操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //// 可以自定义表头的别名
        //writer.addHeaderAlias("teamId","用户名");
        // 一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list, true);
        String fileName = "参赛队伍信息表.xlsx";// 文件名
        // 设置浏览器响应格式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    // 设置分数
    @PostMapping("setGrade")
    public void setGrade(@RequestBody SysTeam sysTeam){
        boolean b = service.updateById(sysTeam);
    }

    // 设置队伍奖项
    @GetMapping("setPrice")
    public void setPrice(@RequestParam Integer compid,
                         @RequestParam Integer firstPrice,
                         @RequestParam Integer secondPrice,
                         @RequestParam Integer thirdPrice,
                         @RequestParam Integer excellentPrice,
                         HttpServletRequest request){
        ArrayList<SysTeam> list = new ArrayList<>();
        List<SysTeam> teams = service.getByTeamCompidOrderByTeamGradeDesc(compid, 0, firstPrice);
        for (SysTeam s:teams
             ) {
            s.setTeamPrice("一等奖");
            service.updateById(s);
            list.add(s);
        }
        List<SysTeam> teams1 = service.getByTeamCompidOrderByTeamGradeDesc(compid, firstPrice, secondPrice);
        for (SysTeam s:teams1
        ) {
            s.setTeamPrice("二等奖");
            service.updateById(s);
            list.add(s);
        }
        List<SysTeam> teams2 = service.getByTeamCompidOrderByTeamGradeDesc(compid, firstPrice + secondPrice, thirdPrice);
        for (SysTeam s:teams2
        ) {
            s.setTeamPrice("三等奖");
            service.updateById(s);
            list.add(s);
        }
        List<SysTeam> teams3 = service.getByTeamCompidOrderByTeamGradeDesc(compid, firstPrice + secondPrice + thirdPrice, excellentPrice);
        for (SysTeam s:teams3
        ) {
            s.setTeamPrice("优秀奖");
            service.updateById(s);
            list.add(s);
        }
        List<SysTeam> teams4 = service.list(new QueryWrapper<SysTeam>().eq("team_compid", compid));
        for (SysTeam s:teams4
             ) {
            if (s.getTeamPrice()==null){
                s.setTeamPrice("未得奖");
                service.updateById(s);
            }
        }
        request.getServletContext().setAttribute("price",list);
    }
}
