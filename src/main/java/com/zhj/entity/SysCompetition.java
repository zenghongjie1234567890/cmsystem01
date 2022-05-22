package com.zhj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhj
 * @since 2022-04-14
 */
@Data
@TableName("sys_competition")
public class SysCompetition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 比赛编号
     */
    @TableId(value = "comp_id",type = IdType.AUTO)
    private Integer compId;

    /**
     * 比赛名字
     */
    private String compName;

    /**
     * 比赛主办方
     */
    private String compSponsor;

    /**
     * 比赛限制人数
     */
    private Integer compMaxnums;

    /**
     * 比赛当前状态
     */
    private String compStatus;

    /**
     * 比赛开始时间
     */
    private LocalDate compStarttime;

    /**
     * 比赛结束时间
     */
    private LocalDate compEndtime;

    /**
     * 比赛简单说明
     */
    private String compDesc;

    /**
     * 比赛创建时间
     */
    private LocalDate compCreatetime;

    @Override
    public String toString() {
        return "SysCompetition{" +
                "compId=" + compId +
                ", compName='" + compName + '\'' +
                ", compSponsor='" + compSponsor + '\'' +
                ", compMaxnums=" + compMaxnums +
                ", compStatus='" + compStatus + '\'' +
                ", compStarttime=" + compStarttime +
                ", compEndtime=" + compEndtime +
                ", compDesc='" + compDesc + '\'' +
                ", createTime=" + compCreatetime +
                '}';
    }

    public SysCompetition(Integer compId, String compName, String compSponsor, Integer compMaxnums, String compStatus, LocalDate compStarttime, LocalDate compEndtime, String compDesc, LocalDate compCreatetime) {
        this.compId = compId;
        this.compName = compName;
        this.compSponsor = compSponsor;
        this.compMaxnums = compMaxnums;
        this.compStatus = compStatus;
        this.compStarttime = compStarttime;
        this.compEndtime = compEndtime;
        this.compDesc = compDesc;
        this.compCreatetime = compCreatetime;
    }

    public SysCompetition() {
    }
}
