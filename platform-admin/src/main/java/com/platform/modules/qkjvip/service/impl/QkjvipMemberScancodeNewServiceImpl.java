/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberScancodeNewServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-30 09:35:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberScancodeNewDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberScancodeNewEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberScancodeNewService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2022-03-30 09:35:18
 */
@Service("qkjvipMemberScancodeNewService")
public class QkjvipMemberScancodeNewServiceImpl extends ServiceImpl<QkjvipMemberScancodeNewDao, QkjvipMemberScancodeNewEntity> implements QkjvipMemberScancodeNewService {

    /**
     * 获取个人扫码城市数量
     *
     * @param unionId
     * @return
     */
    public int selectScanCityCount(String unionId) {
        return baseMapper.selectScanCityCount(unionId);
    }

    /**
     * 获取近一年用户的扫码记录 --可分页
     *
     * @param params
     * @return
     */
    public List<QkjvipMemberScancodeNewEntity> getScanByYear(@Param("params") Map<String, Object> params) {
        return baseMapper.getScanByYear(params);
    }

    /**
     * 获取用户近一年的扫码数量
     *
     * @param unionid
     * @return
     */
    public int getScanCount(String unionid) {
        return baseMapper.getScanCount(unionid);
    }

    @Override
    public List<QkjvipMemberScancodeNewEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberScancodeNewEntity> page = new Query<QkjvipMemberScancodeNewEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberScancodeNewPage(page, params));
    }



    @Override
    public boolean add(QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew) {
        return this.save(qkjvipMemberScancodeNew);
    }

    @Override
    public boolean update(QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew) {
        return this.updateById(qkjvipMemberScancodeNew);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Long[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
