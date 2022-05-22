package com.zhj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
import com.zhj.entity.*;
import com.zhj.service.ISysCompetitionService;
import com.zhj.service.ISysMembersService;
import com.zhj.service.ISysStuService;
import com.zhj.service.ISysTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
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
 * @since 2022-05-03
 */
@RestController
@RequestMapping("members")
public class SysMembersController {

    @Autowired
    private ISysMembersService service;

    @Autowired
    private ISysStuService stuService;

    @Autowired
    private ISysTeamService teamService;

    @Autowired
    private ISysMembersService membersService;

    @Autowired
    private ISysCompetitionService competitionService;

    // 添加
    @PostMapping("add")
    public void addMembers(@RequestBody SysMembers sysMembers) {
        service.save(sysMembers);
    }

    // 查询队长信息
    @GetMapping("getName")
    public List<SysStu> getName(@RequestParam Integer id){
        QueryWrapper<SysMembers> wrapper = new QueryWrapper<>();
        wrapper.eq("member_tearmid",id);
        wrapper.eq("member_isleader",1);
        SysMembers members = service.getOne(wrapper);
        SysStu stu = stuService.getById(members.getMemberStuid());
        ArrayList<SysStu> list = new ArrayList<>();
        list.add(stu);
        return list;
    }

    // 查询队友名字
    @GetMapping("getsonname")
    public List<SysStu> getsonname(@RequestParam Integer id){
        ArrayList<SysStu> list = new ArrayList<>();
        QueryWrapper<SysMembers> wrapper = new QueryWrapper<>();
        wrapper.eq("member_tearmid",id);
        wrapper.eq("member_isleader",0);
        List<SysMembers> members = service.list(wrapper);
        for (SysMembers s:
             members) {
            SysStu stu = stuService.getById(s.getMemberStuid());
            list.add(stu);
        }
        return list;
    }

    // 查询是否参加该比赛
    @GetMapping("isJoin")
    public List<Integer> isJoinComp(@RequestParam Integer compId,@RequestParam String stuId){
        ArrayList<Integer> list = new ArrayList<>();
        List<Integer> team = teamService.getTeam(compId);
        for (Integer id: team
             ) {
            SysMembers one = service.getOne(new QueryWrapper<SysMembers>().eq("member_tearmid",id).eq("member_stuid", stuId));
            if (one!=null){
                list.add(one.getMemberTearmid());
            }
        }
        return list;
    }

    // 查询个人参加比赛情况
    @GetMapping("privateMsg")
    public List<SysTeam> isJoinComp(@RequestParam Integer stuId){
        ArrayList<SysTeam> list = new ArrayList<>();
        List<SysMembers> list1 = membersService.getByMemberStuid(stuId);
        for (SysMembers members: list1
        ) {
            SysTeam one = teamService.getOne(new QueryWrapper<SysTeam>().eq("team_id", members.getMemberTearmid()));
            if (one!=null){
                SysCompetition competition = competitionService.getById(one.getTeamCompid());
                one.setTeamFname(competition.getCompName());
                if (members.getMemberIsleader()){
                    one.setTeamNums("队长");
                }else {
                    one.setTeamNums("队友");
                }
                list.add(one);
            }
        }
        return list;
    }

    // 查询个人获奖情况
    @GetMapping("privatePrice")
    public List<SysTeam> isPrice(@RequestParam Integer stuId){
        ArrayList<SysTeam> list = new ArrayList<>();
        List<SysMembers> list1 = membersService.getByMemberStuid(stuId);
        for (SysMembers members: list1
        ) {
            SysTeam one = teamService.getOne(new QueryWrapper<SysTeam>().eq("team_id", members.getMemberTearmid()));
            if (one!=null && !one.getTeamPrice().equals("未得奖")){
                SysCompetition competition = competitionService.getById(one.getTeamCompid());
                one.setTeamFname(competition.getCompName());
                if (members.getMemberIsleader()){
                    one.setTeamNums("队长");
                }else {
                    one.setTeamNums("队友");
                }
                list.add(one);
            }
        }
        return list;
    }
}
