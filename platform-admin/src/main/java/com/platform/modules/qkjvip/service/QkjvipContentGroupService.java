/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.controller.QkjvipContentPushchannelController;
import com.platform.modules.qkjvip.entity.QkjvipContentGroupEntity;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:39
 */
public interface QkjvipContentGroupService extends IService<QkjvipContentGroupEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipContentGroupEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjvipContentGroup 
     * @return 新增结果
     */
    void add(QkjvipContentGroupEntity qkjvipContentGroup);

    /**
     * 根据主键更新
     *
     * @param qkjvipContentGroup 
     * @return 更新结果
     */
    void update(QkjvipContentGroupEntity qkjvipContentGroup);
    void update2(QkjvipContentGroupEntity qkjvipContentGroup);
    void updateTaskNoById(QkjvipContentGroupEntity qkjvipContentGroup);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    @Async
    void batchesGetUser(QkjvipContentGroupEntity qkjvipContentGroup, String appidstr) throws IOException;
    String sendWxContent(QkjvipContentGroupEntity qkjvipContentGroup, List<QrtzMemberFansEntity> fansList) throws IOException;
}
