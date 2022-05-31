/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtCommentDao.java
 * 包名称:com.platform.modules.cmnt.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:11        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.cmnt.entity.CmntMgmtCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-05-25 17:16:11
 */
@Mapper
public interface CmntMgmtCommentDao extends BaseMapper<CmntMgmtCommentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<CmntMgmtCommentEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<CmntMgmtCommentEntity> selectCmntMgmtCommentPage(IPage page, @Param("params") Map<String, Object> params);

    List<CmntMgmtCommentEntity> queryList(@Param("params") Map<String, Object> params);
}
