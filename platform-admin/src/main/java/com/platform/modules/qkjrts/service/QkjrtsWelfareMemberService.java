/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareMemberService.java
 * 包名称:com.platform.modules.qkjrts.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareMemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
public interface QkjrtsWelfareMemberService extends IService<QkjrtsWelfareMemberEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareMemberEntity> queryAll(Map<String, Object> params);
    List<QkjvipMemberMessageUserQueryEntity> queryByMainId(String mainid);

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
     * @param qkjrtsWelfareMember 
     * @return 新增结果
     */
    boolean add(QkjrtsWelfareMemberEntity qkjrtsWelfareMember);
    void addBatch(List<QkjrtsWelfareMemberEntity> memberlist);

    /**
     * 根据主键更新
     *
     * @param qkjrtsWelfareMember 
     * @return 更新结果
     */
    boolean update(QkjrtsWelfareMemberEntity qkjrtsWelfareMember);
    boolean updateByMainId(String mainId);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);
    void deleteByMainId(String mainid);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
}
