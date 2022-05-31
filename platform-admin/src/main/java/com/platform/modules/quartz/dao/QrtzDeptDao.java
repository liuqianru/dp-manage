/*
 * 项目名称:platform-plus
 * 类名称:OrgUserDao.java
 * 包名称:com.platform.modules.quartz.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/25 15:04            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.quartz.entity.QrtzDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DeptDao
 *
 * @author liuqianru
 * @date 2020/3/25 15:04
 */
@Mapper
public interface QrtzDeptDao extends BaseMapper<QrtzDeptEntity> {
    List<QrtzDeptEntity> queryAll(@Param("params") Map<String, Object> params);
}
