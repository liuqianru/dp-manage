/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberBasicService.java
 * 包名称:com.platform.modules.tmp.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-20 14:33:13        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.quartz.entity.TmpQkjvipMemberCouponsEntity;

import java.util.List;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-08-20 14:33:13
 */
public interface TmpQkjvipMemberCoupoensService extends IService<TmpQkjvipMemberCouponsEntity> {

    /**
     * 插入优惠券
     *
     * @param mbList 用户
     */
    void addBatch(List<TmpQkjvipMemberCouponsEntity> mbList);
}
