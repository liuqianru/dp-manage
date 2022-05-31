/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberScancodeNewService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-30 09:35:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.QkjvipMemberScancodeNewEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2022-03-30 09:35:18
 */
public interface QkjvipMemberScancodeNewService extends IService<QkjvipMemberScancodeNewEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberScancodeNewEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 获取个人扫码城市数量
     *
     * @param unionId
     * @return
     */
    int selectScanCityCount(String unionId);

    /**
     * 获取近一年用户的扫码记录 --可分页
     * @param params
     * @return
     */
    List<QkjvipMemberScancodeNewEntity> getScanByYear(@Param("params") Map<String,Object> params);

    /**
     * 获取用户近一年的扫码数量
     * @param unionid
     * @return
     */
    int getScanCount(String unionid);

    /**
     * 新增
     *
     * @param qkjvipMemberScancodeNew 
     * @return 新增结果
     */
    boolean add(QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew);

    /**
     * 根据主键更新
     *
     * @param qkjvipMemberScancodeNew 
     * @return 更新结果
     */
    boolean update(QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Long id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(Long[] ids);


}
