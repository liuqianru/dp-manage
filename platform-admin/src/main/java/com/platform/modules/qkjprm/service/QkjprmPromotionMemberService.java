/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionMemberService.java
 * 包名称:com.platform.modules.qkjprm.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
public interface QkjprmPromotionMemberService extends IService<QkjprmPromotionMemberEntity> {

    /**
     * 获取人员数量
     *
     * @param params
     * @return
     */
    Integer getCount(@Param("params") Map<String, Object> params);

    /**
     * 获取人员列表
     *
     * @param params
     * @return
     */
    List<QkjprmPromotionMemberEntity> getList(@Param("params") Map<String, Object> params);

    /**
     * 根据unionid获取用户信息
     *
     * @param unionid
     * @return
     */
    QkjprmPromotionMemberEntity getMemberByUnionid(String unionid);

    /**
     * 获取用户的产品积分
     *
     * @param params
     * @return
     */
    Integer getProductIntegral(@Param("params") Map<String, Object> params);

    /**
     * 更新用户的产品积分
     *
     * @param startDate
     */
    boolean updateProductIntegral(Date startDate);

    /**
     * 获取用户排名
     *
     * @param unionid
     * @return
     */
    Integer getMemberCount(String unionid);

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionMemberEntity> queryAll(Map<String, Object> params);

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
     * @param qkjprmPromotionMember
     * @return 新增结果
     */
    boolean add(QkjprmPromotionMemberEntity qkjprmPromotionMember);

    /**
     * 根据主键更新
     *
     * @param qkjprmPromotionMember
     * @return 更新结果
     */
    boolean update(QkjprmPromotionMemberEntity qkjprmPromotionMember);

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
