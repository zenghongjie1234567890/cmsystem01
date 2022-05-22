package com.zhj.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.SysCompetition;
import com.zhj.entity.SysFile;
import com.zhj.service.ISysCompetitionService;
import com.zhj.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-04-14
 */
@RestController
@RequestMapping("comp")
public class SysCompetitionController {

    @Autowired
    private ISysCompetitionService iSysCompetitionService;

    @Autowired
    private ISysFileService iSysFileService;

    void changeStatus(){
        List<SysCompetition> list = iSysCompetitionService.list();
        for (SysCompetition sysCompetition : list
        ) {
            LocalDate starttime = sysCompetition.getCompStarttime();
            LocalDate endtime = sysCompetition.getCompEndtime();
            Date start = Date.from(starttime.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endtime.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (System.currentTimeMillis() < start.getTime()) {
                sysCompetition.setCompStatus("未开始");
                iSysCompetitionService.updateById(sysCompetition);
            }
            if (System.currentTimeMillis() > end.getTime()) {
                sysCompetition.setCompStatus("已结束");
                iSysCompetitionService.updateById(sysCompetition);
            }
            if (start.getTime()<System.currentTimeMillis() && end.getTime()>System.currentTimeMillis()) {
                sysCompetition.setCompStatus("进行中");
                iSysCompetitionService.updateById(sysCompetition);
            }
        }
    }

    // 新增信息
    @PostMapping("addComp")
    public Map<String, Object> addStu(@RequestBody SysCompetition sysCompetition, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            LocalDate now = LocalDate.now();
            sysCompetition.setCompCreatetime(now);
            boolean save = iSysCompetitionService.save(sysCompetition);
            QueryWrapper<SysCompetition> wrapper = new QueryWrapper<>();
            wrapper.eq("comp_name", sysCompetition.getCompName());
            SysCompetition one = iSysCompetitionService.getOne(wrapper);
            request.getServletContext().setAttribute("num", one.getCompId());
            changeStatus();
            map.put("msg", save);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 文件上传
    @PostMapping("upload")
    public Map<String, Object> upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        int num = (Integer) request.getServletContext().getAttribute("num");
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        File file1 = new File("E:\\CodeRoom\\competition_managerSystem\\cmsystem\\src\\main\\resources\\static\\file", uuid + StrUtil.DOT + type);
        file.transferTo(file1);
        String url = "http://localhost:8055/comp/" + uuid + StrUtil.DOT + type;
        iSysFileService.save(new SysFile(num, filename,url));
        return map;
    }

    // 下载文件
    @GetMapping("{fileuuid}")
    public void download(@PathVariable String fileuuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File file1 = new File("C:\\Users\\Admin\\Desktop\\cmsystem\\src\\main\\resources\\static\\file", fileuuid);
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.like("file_url",fileuuid);
        SysFile one = iSysFileService.getOne(wrapper);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(one.getFileName(), "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(file1));
        os.flush();
        os.close();
    }


    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage4(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String compName,
                                        @RequestParam(defaultValue = "") String compStatus) {
        IPage<SysCompetition> page = new Page<>(currentPage, pageSize);
        changeStatus();
        QueryWrapper<SysCompetition> wrapper = new QueryWrapper<>();
        wrapper.like("comp_name", compName);
        wrapper.like("comp_status", compStatus);
        IPage<SysCompetition> page1 = iSysCompetitionService.page(page,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", page1);
        return map;
    }

     // 更新信息
    @PostMapping("updateComp")
    public Map<String, Object> UpdateStu(@RequestBody SysCompetition sysCompetition) {
        System.out.println(sysCompetition);
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            boolean b1 = iSysCompetitionService.updateById(sysCompetition);
            changeStatus();
            map.put("msg", b1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 删除
    @GetMapping("delComp/{compId}")
    public void delComp(@PathVariable("compId") Integer compId){
        iSysCompetitionService.removeById(compId);
    }


    // 查询特定的比赛信息
    @GetMapping("selOne")
    public SysCompetition selOne(@RequestParam String id){
        SysCompetition byId = iSysCompetitionService.getById(id);
        return byId;
    }
}
