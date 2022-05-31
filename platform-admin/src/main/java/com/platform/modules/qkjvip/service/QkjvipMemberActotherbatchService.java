/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActotherbatchService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberActotherbatchEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
public interface QkjvipMemberActotherbatchService extends IService<QkjvipMemberActotherbatchEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActotherbatchEntity> queryAll(Map<String, Object> params);

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
     * @param qkjvipMemberActotherbatch 
     * @return 新增结果
     */
    boolean add(QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberActotherbatch 
     * @return 更新结果
     */
    boolean update(QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch);

    /**
     * 根据主键删除
     *
     * @param mainId mainId
     * @return 删除结果
     */
    boolean delete(String mainId);

    /**
     * 根据主键批量删除
     *
     * @param mainIds mainIds
     * @return 删除结果
     */
    boolean deleteBatch(String[] mainIds);
}
