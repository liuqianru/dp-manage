/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareBirthdayService.java
 * 包名称:com.platform.modules.qkjrts.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-13 12:20:09        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareBirthdayEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2022-04-13 12:20:09
 */
public interface QkjrtsWelfareBirthdayService extends IService<QkjrtsWelfareBirthdayEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareBirthdayEntity> queryAll(Map<String, Object> params);

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
     * @param qkjrtsWelfareBirthday 
     * @return 新增结果
     */
    boolean add(QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday);

    /**
     * 根据主键更新
     *
     * @param qkjrtsWelfareBirthday 
     * @return 更新结果
     */
    boolean update(QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday);

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
}
