/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupmemberService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-26 13:18:34        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupmemberEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2020-10-26 13:18:34
 */
public interface QkjvipMemberSignupmemberService extends IService<QkjvipMemberSignupmemberEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberSignupmemberEntity> queryAll(Map<String, Object> params);

    /**
     * 查询是否存在
     *
     * @param params 查询参数
     * @return List
     */

    List<QkjvipMemberSignupmemberEntity> queryTopOne(Map<String, Object> params);

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
     * @param qkjvipMemberSignupmember 
     * @return 新增结果
     */
    boolean add(QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberSignupmember 
     * @return 更新结果
     */
    boolean update(QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember);

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
