/*
 * 项目名称:platform-plus
 * 类名称:MemberDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 16:48            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.pageCont.pageCount;
import com.platform.modules.qkjvip.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberDao
 *
 * @author liuqianru
 * @date 2020/3/9 16:48
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberEntity> queryAll(@Param("params") Map<String, Object> params);

    List<MemberEntity> selectMemberByOpenid(@Param("params") Map<String, Object> params);

    List<MemberEntity> selectMemberByUnionid(@Param("params") Map<String, Object> params);

    List<MemberEntity> selectMemberByJuruMemberid(@Param("params") Map<String, Object> params);

    List<MemberEntity> queryAllByMobile(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<MemberEntity> selectMemberList(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询-带会员标签
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<MemberEntity> selectMemberList2(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询条数
     *
     * @param params 查询参数
     * @return List
     */
    pageCount selectMemberCount(@Param("params") Map<String, Object> params);

    /**
     * 逻辑批量删除
     *
     * @param memberIds memberIds
     * @return int
     */
    void removeByIds(String[] memberIds);

    List<MemberEntity> queryMemByList(List<MemberEntity> mbList);

    int selectQkhMemberById(String memberId);

    List<MemberEntity> queryMemberInfo(String mobile);
}
