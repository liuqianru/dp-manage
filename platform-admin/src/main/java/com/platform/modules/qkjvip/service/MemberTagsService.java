/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberLabelService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 09:00:34        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberTagsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author liuqianru
 * @date 2020-08-26 09:00:34
 */
public interface MemberTagsService extends IService<MemberTagsEntity> {
    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberTagsEntity> queryAll(Map<String, Object> params);

    /**
     * saveOrUpdate
     *
     * @param member 用户member
     */
    void saveOrUpdate(MemberEntity member);

    /**
     * 根据用户ID，获取标签ID列表
     *
     * @param params 用户Id
     * @return List
     */
    List<MemberTagsEntity> queryTagsList(Map<String, Object> params);

    /**
     * 根据会员ID数组，批量删除会员标签
     *
     * @param memberIds memberIds
     * @return int
     */
    int deleteBatch(String[] memberIds);

    /**
     * 根据tagid删除
     *
     * @param tagId tagId
     * @return 删除结果
     */
    int delete(String tagId);

}
