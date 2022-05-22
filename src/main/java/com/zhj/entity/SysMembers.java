package com.zhj.entity;

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
@TableName("sys_members")
public class SysMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 队伍id
     */
    private Integer memberTearmid;

    /**
     * 学生id
     */
    private Integer memberStuid;

    /**
     * 是否为队长
     */
    private Boolean memberIsleader;


}
