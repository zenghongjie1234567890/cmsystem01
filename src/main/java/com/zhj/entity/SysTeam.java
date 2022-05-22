package com.zhj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_team")
public class SysTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 团队id
     */
    @TableId(value = "team_id", type = IdType.AUTO)
    private Integer teamId;
    /**
     * 团队名称
     */
    private String teamName;
    /**
     * 作品名字
     */
    private String teamWorks;
    /**
     * 团队作品
     */
    private String teamFile;
    /**
     * 附件名字
     */
    private String teamFname;
    /**
     * 队伍人数
     */
    private String teamNums;
    /**
     * 作品分数
     */
    private Integer teamGrade;
    /**
     * 作品得奖
     */
    private String teamPrice;
    /**
     * 参加比赛的id
     */
    private Integer teamCompid;
    /**
     * 指导老师id
     */
    private String teamTeaid;

}
