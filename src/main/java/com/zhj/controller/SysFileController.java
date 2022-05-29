package com.zhj.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.SysFile;
import com.zhj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-04-17
 */
@RestController
@RequestMapping("file")
public class SysFileController {

    @Autowired
    private ISysFileService iSysFileService;

    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String compId,
                                        @RequestParam(defaultValue = "") String compName) {
        IPage<SysFile> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.like("comp_id", compId);
        wrapper.like("file_name", compName);
        IPage<SysFile> page1 = iSysFileService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", page1);
        return map;
    }

    // 更新信息
    @PostMapping("updateFile")
    public Map<String, Object> UpdateStu(@RequestBody SysFile sysFile) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            boolean b1 = iSysFileService.updateById(sysFile);
            map.put("msg", b1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    @PostMapping("addFile")
    public Map<String, Object> addStu(@RequestBody SysFile sysFile, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Integer id = sysFile.getCompId();
        boolean b = true;
        try {
            request.getServletContext().setAttribute("filenum", id );
            map.put("msg", b);
        } catch (Exception e) {
            b=false;
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 文件上传
    @PostMapping("upload")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        int num = (Integer) request.getServletContext().getAttribute("filenum");
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        File file1 = new File("/www/wwwroot/file", uuid + StrUtil.DOT + type);
        file.transferTo(file1);
        String url = "http://47.107.229.21:8055/comp/" + uuid + StrUtil.DOT + type;
        iSysFileService.save(new SysFile(num, filename,url));
    }

    // 删除
    @PostMapping("del")
    public Map<String, Object> delUser(@RequestBody SysFile sysFile) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.eq("file_url",sysFile.getFileUrl());
        boolean b = iSysFileService.remove(wrapper);
        return map;
    }

    // 查找该竞赛的附件
    @GetMapping("getFiles")
    public List<SysFile> getFiles(@RequestParam Integer compId){
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",compId);
        List<SysFile> list = iSysFileService.list(wrapper);
        return list;
    }
}
