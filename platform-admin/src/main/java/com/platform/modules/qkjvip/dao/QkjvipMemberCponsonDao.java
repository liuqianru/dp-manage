/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponsonDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponsonEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@Mapper
public interface QkjvipMemberCponsonDao extends BaseMapper<QkjvipMemberCponsonEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberCponsonEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberCponsonEntity> selectQkjvipMemberCponsonPage(IPage page, @Param("params") Map<String, Object> params);
    int deleteBatchByOrder(String mainid);
    List<QkjvipMemberMessageUserQueryEntity> queryByCponId(String cponId);
}
