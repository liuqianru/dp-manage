/*
 * 项目名称:platform-plus
 * 类名称:MemberActiveServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 13:36            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.MemberActiveDao;
import com.platform.modules.qkjvip.entity.MemberActiveEntity;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import com.platform.modules.qkjvip.service.MemberActiveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MemberActiveServiceImpl
 *
 * @author liuqianru
 * @date 2020/3/13 13:36
 */
@Service("memberActiveService")
public class MemberActiveServiceImpl extends ServiceImpl<MemberActiveDao, MemberActiveEntity> implements MemberActiveService {
    @Override
    public List<MemberActiveEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "ma.add_time");
        params.put("asc", false);
        Page<MemberActiveEntity> page = new Query<MemberActiveEntity>(params).getPage();
        page.setRecords(baseMapper.selectMemberActiveList(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public void add(MemberActiveEntity mactive, Map<String, Object> params) {
        this.save(mactive);
    }

    @Override
    public void add(MemberActiveEntity mactive) {
        this.save(mactive);
    }

    @Override
    public void update(MemberActiveEntity mactive, Map<String, Object> params) {
        this.updateById(mactive);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] uuids) {
        this.removeByIds(Arrays.asList(uuids));
    }
}
