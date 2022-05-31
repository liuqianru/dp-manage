/*
 * 项目名称:platform-plus
 * 类名称:MemberService.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 14:25            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.pageCont.pageCount;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberQueryEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liuqianru
 * @date 2020/3/9 14:25
 */
public interface MemberService extends IService<MemberEntity> {

    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<MemberEntity> queryAll(Map<String, Object> params);
    List<MemberEntity> queryList(MemberQueryEntity memberQuery) throws IOException;
    List<MemberEntity> selectMemberByOpenid(Map<String, Object> params);
    List<MemberEntity> selectMemberByUnionid(Map<String, Object> params);
    List<MemberEntity> selectMemberByJuruMemberid(Map<String, Object> params);
    int selectQkhMemberById(String memberId);

    List<MemberEntity> queryAllByMobile(Map<String, Object> params);
    List<MemberEntity> queryMemberInfo(String mobile);

    /**
     * 查询条数
     *
     * @param params 查询参数
     * @return List
     */
    pageCount selectMemberCount(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 保存会员
     *
     * @param member 会员
     * @param params 查询参数
     */
    void add(MemberEntity member, Map<String, Object> params);
    void add(MemberEntity member);
    void addBatch(List<MemberEntity> mList);

    /**
     * 修改用户
     *
     * @param member 用户
     * @param params 查询参数
     */
    void update(MemberEntity member, Map<String, Object> params) throws IOException;

    /**
     * 删除会员
     *
     * @param userIds 会员Ids
     */
    void deleteBatch(String[] userIds) throws IOException;

    /**
     * 根据list查询会员孙珊珊
     * @param mbList
     * @return
     */
    List<MemberEntity> queryMemByList(List<MemberEntity> mbList);
}
