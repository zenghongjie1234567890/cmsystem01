package com.zhj.service;

import com.zhj.entity.SysMembers;
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
public interface ISysMembersService extends IService<SysMembers> {
    List<SysMembers> getByMemberStuid( Integer memberStuid);
}
