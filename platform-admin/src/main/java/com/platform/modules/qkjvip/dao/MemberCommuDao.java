/*
 * 项目名称:platform-plus
 * 类名称:MemberCommuDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 14:01            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.qkjvip.entity.MemberCommuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberCommuDao
 *
 * @author liuqianru
 * @date 2020/3/13 14:01
 */
@Mapper
public interface MemberCommuDao extends BaseMapper<MemberCommuEntity> {

    List<MemberCommuEntity> queryAll(@Param("params") Map<String, Object> params);

    List<MemberCommuEntity> selectMemberCommuList(IPage page, @Param("params") Map<String, Object> params);
}
