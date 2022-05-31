/*
 * 项目名称:platform-plus
 * 类名称:MemberAddressServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/12 11:38            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.MemberAddressDao;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.service.MemberAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MemberAddressServiceImpl
 *
 * @author liuqianru
 * @date 2020/3/12 11:38
 */
@Service("memberAddressService")
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressDao, MemberAddressEntity> implements MemberAddressService {

    @Override
    public List<MemberAddressEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "ma.default_address desc, ma.add_time");
        params.put("asc", false);
        Page<MemberAddressEntity> page = new Query<MemberAddressEntity>(params).getPage();
        page.setRecords(baseMapper.selectMemberAddrList(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MemberAddressEntity maddress, Map<String, Object> params) {
        if (maddress.getDefaultAddress() == 1) {  // 设置了默认地址
            // 将其他地址修改为非默认
            updateByMemberId(maddress.getMemberId());
        }
        this.save(maddress);
    }

    @Override
    public void add(MemberAddressEntity maddress) {
        this.save(maddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MemberAddressEntity maddress, Map<String, Object> params) {
        if (maddress.getDefaultAddress() == 1) {  // 设置了默认地址
            // 将其他地址修改为非默认
            updateByMemberId(maddress.getMemberId());
        }
        this.updateById(maddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] uuids) {
        this.removeByIds(Arrays.asList(uuids));
    }

    @Override
    public void deleteByMemberId(String memberId) {
        baseMapper.deleteByMemberId(memberId);
    }

    public void updateByMemberId(String memberId) {
        baseMapper.updateByMemberId(memberId);
    }
}
