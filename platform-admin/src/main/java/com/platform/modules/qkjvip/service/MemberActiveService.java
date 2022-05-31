/*
 * 项目名称:platform-plus
 * 类名称:MemberActiveService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 13:28            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.MemberActiveEntity;

import java.util.List;
import java.util.Map;

/**
 * MemberActiveService
 *
 * @author liuqianru
 * @date 2020/3/13 13:28
 */
public interface MemberActiveService extends IService<MemberActiveEntity> {
    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberActiveEntity> queryAll(Map<String, Object> params);
    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);
    /**
     * 保存活动记录
     *
     * @param mactive 会员
     * @param params 查询参数
     */
    void add(MemberActiveEntity mactive, Map<String, Object> params);
    void add(MemberActiveEntity mactive);
    /**
     * 修改活动记录
     *
     * @param mactive 用户
     * @param params 查询参数
     */
    void update(MemberActiveEntity mactive, Map<String, Object> params);
    /**
     * 删除活动
     *
     * @param uuids 会员Ids
     */
    void deleteBatch(String[] uuids);
}
