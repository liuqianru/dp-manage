/*
 * 项目名称:platform-plus
 * 类名称:SysOrgUpdatelogDao.java
 * 包名称:com.platform.modules.sys.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 13:52:13        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-08-04 13:52:13
 */
@Mapper
public interface SysOrgUpdatelogDao extends BaseMapper<SysOrgUpdatelogEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysOrgUpdatelogEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<SysOrgUpdatelogEntity> selectSysOrgUpdatelogPage(IPage page, @Param("params") Map<String, Object> params);
}
