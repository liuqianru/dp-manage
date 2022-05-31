/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberImportDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-25 08:41:43        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberImportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 李鹏军
 * @date 2020-09-25 08:41:43
 */
@Mapper
public interface QkjvipMemberImportDao extends BaseMapper<QkjvipMemberImportEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberImportEntity> queryAll(@Param("params") Map<String, Object> params);

    List<QkjvipMemberImportEntity> selectMemberByMobile(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberImportEntity> selectQkjvipMemberImportPage(IPage page, @Param("params") Map<String, Object> params);
}
