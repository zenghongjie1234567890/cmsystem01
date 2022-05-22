package com.zhj.entity;

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
 * @since 2022-04-17
 */
@Data
@TableName("sys_file")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 竞赛的ID
     */
    private Integer compId;

    /**
     * 文件名字
     */
    private String fileName;

    /**
     * 竞赛的文件上传地址
     */
    @TableId(value = "file_url")
    private String fileUrl;


    @Override
    public String toString() {
        return "SysFile{" +
            "compId=" + compId +
            ", fileName=" + fileName +
            ", fileUrl=" + fileUrl +
        "}";
    }

    public SysFile(Integer compId, String fileName, String fileUrl) {
        this.compId = compId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public SysFile() {
    }
}
