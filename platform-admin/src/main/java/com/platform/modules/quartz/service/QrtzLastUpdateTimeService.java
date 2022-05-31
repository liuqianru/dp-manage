/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberFansService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-09 14:02:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.quartz.entity.QrtzLastUpdateTimeEntity;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
public interface QrtzLastUpdateTimeService extends IService<QrtzLastUpdateTimeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzLastUpdateTimeEntity> queryAll(Map<String, Object> params);

    /**
     * 修改最后更新时间
     *
     * @param updateTimeEntity 用户
     */
    void updateLastDatetime(QrtzLastUpdateTimeEntity updateTimeEntity);
}
