/*
 * 项目名称:platform-plus
 * 类名称:MemberCommuService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 14:10            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import com.platform.modules.qkjvip.entity.MemberCommuEntity;

import java.util.List;
import java.util.Map;

/**
 * MemberCommuService
 *
 * @author liuqianru
 * @date 2020/3/13 14:10
 */
public interface MemberCommuService extends IService<MemberCommuEntity> {
    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberCommuEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 保存沟通记录
     *
     * @param mcommu 会员
     * @param params 查询参数
     */
    void add(MemberCommuEntity mcommu, Map<String, Object> params);
    void add(MemberCommuEntity mcommu);
    /**
     * 修改沟通记录
     *
     * @param mcommu 用户
     * @param params 查询参数
     */
    void update(MemberCommuEntity mcommu, Map<String, Object> params);

    /**
     * 删除沟通记录
     *
     * @param uuids 会员地址Ids
     */
    void deleteBatch(String[] uuids);
}
