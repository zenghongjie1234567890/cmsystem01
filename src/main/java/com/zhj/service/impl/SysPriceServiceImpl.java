package com.zhj.service.impl;

import com.zhj.entity.SysPrice;
import com.zhj.mapper.SysPriceMapper;
import com.zhj.service.ISysPriceService;
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
 * @since 2022-05-13
 */
@Service
public class SysPriceServiceImpl extends ServiceImpl<SysPriceMapper, SysPrice> implements ISysPriceService {

    @Autowired
    private SysPriceMapper sysPriceMapper;

    @Override
    public String getCompName(Integer compid, Integer front) {
        String name = sysPriceMapper.getCompName(compid, front);
        return name;
    }

    @Override
    public List<System> getPrice(Integer compid) {
        List<System> price = sysPriceMapper.getPrice(compid);
        return price;
    }

}
