package com.zhj.cmsystem;

import com.zhj.entity.SysCompetition;
import com.zhj.entity.SysFile;
import com.zhj.entity.SysTeacher;
import com.zhj.entity.SysTeam;
import com.zhj.mapper.SysTeacherMapper;
import com.zhj.mapper.SysTeamMapper;
import com.zhj.service.ISysCompetitionService;
import com.zhj.service.ISysFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootTest
class CmsystemApplicationTests {

    @Autowired
    private ISysCompetitionService service;

    @Autowired
    private ISysFileService iSysFileService;


    @Test
    void contextLoads() {
        SysCompetition tem = service.getById(1);
        LocalDate starttime = tem.getCompStarttime();
        LocalDate endtime = tem.getCompEndtime();
        Date start = Date.from(starttime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endtime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (System.currentTimeMillis()>start.getTime()){
            System.out.println("过时了");
        }
        if (System.currentTimeMillis()<end.getTime()){
            System.out.println("还没到期");
        }
    }

    @Test
    public void fw(){
        List<SysFile> list = iSysFileService.list();
        for (SysFile obj: list
             ) {
            System.out.println(obj.getFileUrl());
        }
    }

    @Autowired
    private SysTeacherMapper teacherMapper;

    @Autowired
    private SysTeamMapper sysTeamMapper;

    @Test
    public void fwef(){
        List<SysTeam> list = sysTeamMapper.getByTeamCompidOrderByTeamGradeDesc(10006, 0, 2);
        System.out.println(list );
    }
}
