/*
 * 项目名称:platform-plus
 * 类名称:MemberAddressDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/12 11:41            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberAddressDao
 *
 * @author liuqianru
 * @date 2020/3/12 11:41
 */
@Mapper
public interface MemberAddressDao extends BaseMapper<MemberAddressEntity> {
    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberAddressEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<MemberAddressEntity> selectMemberAddrList(IPage page, @Param("params") Map<String, Object> params);

    void updateByMemberId(String memberId);

    void deleteByMemberId(String memberId);
}
