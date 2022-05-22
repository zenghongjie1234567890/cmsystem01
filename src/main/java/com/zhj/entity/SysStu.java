package com.zhj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhj
 * @since 2022-04-09
 */
@Data
@TableName("sys_stu")
public class SysStu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生学号
     */
    @TableId(value = "stu_id")
    private String stuId;

    /**
     * 学生所属学院
     */
    private String stuCollege;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 学生班级
     */
    private String stuClass;

    /**
     * 学生密码	
     */
    private String stuPwd;

    /**
     * 学生联系QQ
     */
    private String stuQq;

    /**
     * 学生图片
     */
    private String stuPicture;

    public SysStu(String stuId, String stuCollege, String stuName, String stuClass, String stuPwd, String stuQq, String stuPicture) {
        this.stuId = stuId;
        this.stuCollege = stuCollege;
        this.stuName = stuName;
        this.stuClass = stuClass;
        this.stuPwd = stuPwd;
        this.stuQq = stuQq;
        this.stuPicture = stuPicture;
    }

    public SysStu() {
    }
}
