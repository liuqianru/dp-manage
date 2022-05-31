/*
 * 项目名称:platform-plus
 * 类名称:SysUserSuperviseDao.java
 * 包名称:com.platform.modules.sys.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 09:26:18        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.sys.entity.SysUserSuperviseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-08-04 09:26:18
 */
@Mapper
public interface SysUserSuperviseDao extends BaseMapper<SysUserSuperviseEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysUserSuperviseEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<SysUserSuperviseEntity> selectSysUserSupervisePage(IPage page, @Param("params") Map<String, Object> params);
}
