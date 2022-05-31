/*
 * 项目名称:platform-plus
 * 类名称:MemberDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/7 16:48            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.quartz.entity.QrtzMemberBasicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberBasicDao
 *
 * @author liuqianru
 * @date 2020/8/7 16:48
 */
@Mapper
public interface QrtzMemberBasicDao extends BaseMapper<QrtzMemberBasicEntity> {
    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzMemberBasicEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 查询所有列表
     * @return List
     */
    List<QrtzMemberBasicEntity> queryList();
}
