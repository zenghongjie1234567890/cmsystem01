package com.zhj.controller;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhj.entity.SysStu;
import com.zhj.service.ISysStuService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhj
 * @since 2022-04-09
 */
@RestController
@RequestMapping("stu")
public class SysStuController {

    @Autowired
    private ISysStuService iSysStuService;

    // 保存学生图片
    @PostMapping("savePicture")
    // 图片参数名一定要和前端的一样 @RequestParam("photo")该注解可以用别名，解决前后端命名参数名字不一样
    public String saveEmp(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(),"static/stu_picture/");
        if(!upload.exists()) {
            upload.mkdirs();
        }
        String parent= upload.getPath();
        // 头像保存: 保存客户端上传的图像到服务端
        String uuid = IdUtil.fastSimpleUUID();
        String newFileName = uuid + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File(parent, newFileName));
        return "/stu_picture/"+newFileName;
    }

    // 登录
    @PostMapping("login")
    public Map<String,Object> login(@RequestBody SysStu sysStu){
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SysStu> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",sysStu.getStuId());
        SysStu one = iSysStuService.getOne(wrapper);
        if (one==null){
            map.put("msg","该学号不存在！！请点击尝试");
        }else {
            wrapper.eq("stu_pwd",sysStu.getStuPwd());
            SysStu one1 = iSysStuService.getOne(wrapper);
            if (one1==null){
                map.put("msg","密码输入错误，请重新尝试！！");
            }else {
                map.put("msg",true);
                map.put("stu",one);
            }
        }
        return map;
    }

    // 新增学生信息
    @PostMapping("addstu")
    public Map<String, Object> addStu(@RequestBody SysStu sysStu) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            boolean save = iSysStuService.save(sysStu);
            map.put("msg", save);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 更新学生信息
    @PostMapping("updatestu")
    public Map<String, Object> UpdateStu(@RequestBody SysStu sysStu) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = false;
        try {
            boolean b1 = iSysStuService.updateById(sysStu);
            map.put("msg", b1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", b);
        }
        return map;
    }

    // 分页
    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String stuId,
                                        @RequestParam(defaultValue = "") String stuCollege,
                                        @RequestParam(defaultValue = "") String stuName,
                                        @RequestParam(defaultValue = "") String stuClass,
                                        HttpServletRequest request) {
        IPage<SysStu> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SysStu> wrapper = new QueryWrapper<>();
        wrapper.like("stu_id", stuId);
        wrapper.like("stu_name", stuName);
        wrapper.like("stu_class", stuClass);
        if (StringUtils.isBlank(stuCollege)){
            List<SysStu> list = iSysStuService.list(wrapper);
            // 将查询的结构放入servletContext作用域，便于下一个请求域提取值
            request.getServletContext().setAttribute("list",list);
        }else {
            wrapper.eq("stu_college", stuCollege);
            List<SysStu> list = iSysStuService.list(wrapper);
            // 将查询的结构放入servletContext作用域，便于下一个请求域提取值
            request.getServletContext().setAttribute("list",list);
        }
        IPage<SysStu> iPage = iSysStuService.page(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", iPage);
        return map;
    }

    // 批量删除
    @PostMapping("delmore")
    public Map<String, Object> delUser(@RequestBody List<String> ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean b = iSysStuService.removeByIds(ids);
        return map;
    }

    // 删除单个用户
    @GetMapping("delstu/{id}")
    public Map<String, Object> delUser(@PathVariable("id") String id) {
        System.out.println(id);
        HashMap<String, Object> map = new HashMap<>();
        boolean b = iSysStuService.removeById(id);
        return map;
    }

    // 查询单个用户
    @GetMapping("findstu")
    public SysStu findUser(@RequestParam String stuId) {
        HashMap<String, Object> map = new HashMap<>();
        SysStu stu = iSysStuService.getById(stuId);
        return stu;
    }

    // 导出数据
    @GetMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 查询出所有数据
        List<SysStu> list1 = (List<SysStu>) request.getServletContext().getAttribute("list");
        // 在内存中操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //// 可以自定义表头的别名
        //writer.addHeaderAlias("username","用户名");
        // 一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list1, true);
        String fileName = "学生信息表.xlsx";// 文件名
        // 设置浏览器响应格式
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    // 导入数据
    @PostMapping("import")
    public boolean importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<SysStu> read = reader.readAll(SysStu.class);
        boolean b = iSysStuService.saveBatch(read);
        return b;
    }

}
