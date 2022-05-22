package com.zhj.service.impl;

import com.zhj.entity.SysTeam;
import com.zhj.mapper.SysTeamMapper;
import com.zhj.service.ISysTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
@Service
public class SysTeamServiceImpl extends ServiceImpl<SysTeamMapper, SysTeam> implements ISysTeamService {

    @Autowired
    private SysTeamMapper sysTeamMapper;

    @Override
    public List<Integer> getTeam(Integer compId) {
        List<Integer> team = sysTeamMapper.getTeam(compId);
        return team;
    }

    @Override
    public List<SysTeam> getByTeamCompidOrderByTeamGradeDesc(Integer teamCompid, Integer front, Integer later) {
        List<SysTeam> list = sysTeamMapper.getByTeamCompidOrderByTeamGradeDesc(teamCompid, front, later);
        return list;
    }
}
