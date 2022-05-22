package com.zhj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhj
 * @since 2022-04-11
 */
@Data
@TableName("sys_teacher")
public class SysTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 老师编号id
     */
    @TableId(value = "teacher_id" ,type = IdType.AUTO)
    private Integer teacherId;

    /**
     * 老师姓名
     */
    private String teacherName;

    /**
     * 老师头像
     */
    private String teacherPicture;

    /**
     * 老师隶属学院
     */
    private String teacherCollege;

    /**
     * 老师学历等级
     */
    private String teacherLevel;

    /**
     * 老师描述
     */
    private String teacherDesc;

    public SysTeacher(Integer teacherId, String teacherName, String teacherPicture, String teacherCollege, String teacherLevel, String teacherDesc) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPicture = teacherPicture;
        this.teacherCollege = teacherCollege;
        this.teacherLevel = teacherLevel;
        this.teacherDesc = teacherDesc;
    }

    public SysTeacher() {
    }
}
