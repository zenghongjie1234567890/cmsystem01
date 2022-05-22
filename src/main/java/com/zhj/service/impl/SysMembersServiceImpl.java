package com.zhj.service.impl;

import com.zhj.entity.SysMembers;
import com.zhj.mapper.SysMembersMapper;
import com.zhj.service.ISysMembersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhj
 * @since 2022-05-03
 */
@Service
public class SysMembersServiceImpl extends ServiceImpl<SysMembersMapper, SysMembers> implements ISysMembersService {

    @Autowired
    private SysMembersMapper sysMembersMapper;

    @Override
    public List<SysMembers> getByMemberStuid(Integer memberStuid) {
        List<SysMembers> members = sysMembersMapper.getByMemberStuid(memberStuid);
        return members;
    }
}
