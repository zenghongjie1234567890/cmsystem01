package com.zhj.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhj
 * @since 2022-05-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_price")
public class SysPrice  implements Serializable{

    /**
     * 比赛ID
     */
    @TableId(value = "compid")
    private Integer compid;

    /**
     * 获奖文件地址
     */
    @TableField("fileUrl")
    private String fileUrl;

    /**
     * 发布时间
     */
    @TableField("fileData")
    private LocalDate fileData;

    /**
     * 文件名称
     */
    @TableField("fileName")
    private String fileName;

}
