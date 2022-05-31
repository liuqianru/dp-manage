/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberImportService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-25 08:41:43        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberImportEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-09-25 08:41:43
 */
public interface QkjvipMemberImportService extends IService<QkjvipMemberImportEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberImportEntity> queryAll(Map<String, Object> params);

    List<QkjvipMemberImportEntity> selectMemberByMobile(Map<String, Object> params);

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
     * @param qkjvipMemberImport 
     * @return 新增结果
     */
    boolean add(QkjvipMemberImportEntity qkjvipMemberImport);

    /**
     * 批量新增
     *
     * @param mList
     * @return 新增结果
     */
    void addBatch(List<QkjvipMemberImportEntity> mList);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberImport 
     * @return 更新结果
     */
    boolean update(QkjvipMemberImportEntity qkjvipMemberImport);

    /**
     * 根据主键删除
     *
     * @param memberId memberId
     * @return 删除结果
     */
    boolean delete(String memberId);

    /**
     * 根据主键批量删除
     *
     * @param memberIds memberIds
     * @return 删除结果
     */
    boolean deleteBatch(String[] memberIds);
}
