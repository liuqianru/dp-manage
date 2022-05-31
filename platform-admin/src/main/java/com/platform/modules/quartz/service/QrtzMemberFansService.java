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
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
public interface QrtzMemberFansService extends IService<QrtzMemberFansEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzMemberFansEntity> queryAll(Map<String, Object> params);

    /**
     * 查询所有
     *
     * @return List
     */
    List<QrtzMemberFansEntity> queryList();

    /**
     * 查询所有
     *
     * @return List
     */
    List<QrtzMemberFansEntity> queryByMemberMessageQuery(Map<String, Object> params);

    /**
     * 插入会员信息
     *
     * @param fanList 用户
     */
    void addBatch(List<QrtzMemberFansEntity> fanList);

    /**
     * 批量修改会员
     *
     * @param fanList fanList
     */
    void updateBatch(List<QrtzMemberFansEntity> fanList);
}
