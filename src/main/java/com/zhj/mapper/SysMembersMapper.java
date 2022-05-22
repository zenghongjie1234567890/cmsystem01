package com.zhj.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.zhj.entity.SysMembers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
@Mapper
public interface SysMembersMapper extends BaseMapper<SysMembers> {
    List<SysMembers> getByMemberStuid(@Param("memberStuid") Integer memberStuid);
}
