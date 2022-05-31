/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionMemberServiceImpl.java
 * 包名称:com.platform.modules.qkjprm.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjprm.dao.QkjprmPromotionMemberDao;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionMemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Service("qkjprmPromotionMemberService")
public class QkjprmPromotionMemberServiceImpl extends ServiceImpl<QkjprmPromotionMemberDao, QkjprmPromotionMemberEntity> implements QkjprmPromotionMemberService {

    /**
     * 获取人员数量
     *
     * @param params
     * @return
     */
    public Integer getCount(@Param("params") Map<String, Object> params) {
        return baseMapper.getCount(params);
    }

    /**
     * 获取人员列表
     *
     * @param params
     * @return
     */
    public List<QkjprmPromotionMemberEntity> getList(@Param("params") Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int size = Convert.toInt(params.get("limit"));
        params.put("pagestart", (page - 1) * size);
        params.put("pagesize", size);

        return baseMapper.getList(params);
    }

    /**
     * 根据unionid获取用户信息
     *
     * @param unionid
     * @return
     */
    public QkjprmPromotionMemberEntity getMemberByUnionid(String unionid) {
        return baseMapper.getMemberByUnionid(unionid);
    }

    /**
     * 获取用户的产品积分
     *
     * @param params
     * @return
     */
    public Integer getProductIntegral(@Param("params") Map<String, Object> params) {
        return baseMapper.getProductIntegral(params);
    }

    /**
     * 更新用户的产品积分
     *
     * @param startDate
     */
    public boolean updateProductIntegral(Date startDate) {
        return baseMapper.updateProductIntegral(startDate);
    }

    /**
     * 获取用户排名
     *
     * @param unionid
     * @return
     */
    public Integer getMemberCount(String unionid) {
        return baseMapper.getMemberCount(unionid);
    }

    @Override
    public List<QkjprmPromotionMemberEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "totalinteger");
        params.put("asc", false);
        Page<QkjprmPromotionMemberEntity> page = new Query<QkjprmPromotionMemberEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjprmPromotionMemberPage(page, params));
    }

    @Override
    public boolean add(QkjprmPromotionMemberEntity qkjprmPromotionMember) {
        return this.save(qkjprmPromotionMember);
    }

    @Override
    public boolean update(QkjprmPromotionMemberEntity qkjprmPromotionMember) {
        return this.updateById(qkjprmPromotionMember);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
