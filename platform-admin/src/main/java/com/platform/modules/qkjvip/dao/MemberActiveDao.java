/*
 * 项目名称:platform-plus
 * 类名称:MemberActiveDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 13:38            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.qkjvip.entity.MemberActiveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberActiveDao
 *
 * @author liuqianru
 * @date 2020/3/13 13:38
 */
@Mapper
public interface MemberActiveDao extends BaseMapper<MemberActiveEntity> {
    List<MemberActiveEntity> queryAll(@Param("params") Map<String, Object> params);

    List<MemberActiveEntity> selectMemberActiveList(IPage page, @Param("params") Map<String, Object> params);
}
