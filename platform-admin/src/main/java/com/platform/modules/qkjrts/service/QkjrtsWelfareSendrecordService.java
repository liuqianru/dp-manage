/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareSendrecordService.java
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
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberImportQueryEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
public interface QkjrtsWelfareSendrecordService extends IService<QkjrtsWelfareSendrecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareSendrecordEntity> queryAll(Map<String, Object> params);
    List<QkjrtsWelfareSendrecordEntity> getWelfareList(Map<String, Object> params);

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
     * @param qkjrtsWelfareSendrecord 
     * @return 新增结果
     */
    boolean add(QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord);
    void addBatch(List<QkjrtsWelfareSendrecordEntity> sendrecordList);

    @Async
    void addBatchByMember(MemberImportQueryEntity memberImportQueryEntity, List<MemberEntity> memberList);

    /**
     * 根据主键更新
     *
     * @param qkjrtsWelfareSendrecord 
     * @return 更新结果
     */
    boolean update(QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord);
    boolean setWelfareStatus(List<String> idList);

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
