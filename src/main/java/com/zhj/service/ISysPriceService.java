package com.zhj.service;

import com.zhj.entity.SysPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhj.entity.SysTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhj
 * @since 2022-05-13
 */
public interface ISysPriceService extends IService<SysPrice> {
    String getCompName( Integer compid,  Integer front);
    List<SysTeam> getPrice(Integer compid);

}
