/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberFansService.java
 * 包名称:com.platform.modules.tmp.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-14 13:51:21        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.quartz.entity.TmpQkjvipMemberBasicEntity;
import com.platform.modules.quartz.entity.TmpQkjvipMemberFansEntity;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-09-14 13:51:21
 */
public interface TmpQkjvipMemberFansService extends IService<TmpQkjvipMemberFansEntity> {

    /**
     * 插入粉丝信息
     *
     * @param fanList 用户
     */
    void addBatch(List<TmpQkjvipMemberFansEntity> fanList);
}
