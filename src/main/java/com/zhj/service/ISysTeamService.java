package com.zhj.service;

import com.zhj.entity.SysTeam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
public interface ISysTeamService extends IService<SysTeam> {
    List<Integer> getTeam(Integer compId);
    List<SysTeam> getByTeamCompidOrderByTeamGradeDesc(Integer teamCompid, Integer front,  Integer later);
}
