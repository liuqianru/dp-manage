/*
 * 项目名称:platform-plus
 * 类名称:MemberAddressService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/12 11:37            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * MemberAddressService
 *
 * @author liuqianru
 * @date 2020/3/12 11:37
 */
public interface MemberAddressService extends IService<MemberAddressEntity> {
    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberAddressEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 保存会员
     *
     * @param maddress 会员
     * @param params 查询参数
     */
    void add(MemberAddressEntity maddress, Map<String, Object> params);
    void add(MemberAddressEntity maddress);
    /**
     * 修改用户
     *
     * @param maddress 用户
     * @param params 查询参数
     */
    void update(MemberAddressEntity maddress, Map<String, Object> params);

    /**
     * 删除会员
     *
     * @param uuids 会员地址Ids
     */
    void deleteBatch(String[] uuids);
    void deleteByMemberId(String memberId);
}
