/*
 * 项目名称:platform-plus
 * 类名称:MemberBasicServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/7 15:48            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.modules.quartz.dao.QrtzMemberBasicDao;
import com.platform.modules.quartz.dao.QrtzMemberCouponsDao;
import com.platform.modules.quartz.entity.QrtzMemberBasicEntity;
import com.platform.modules.quartz.entity.QrtzMemberCouponsEntity;
import com.platform.modules.quartz.service.QrtzMemberBasicService;
import com.platform.modules.quartz.service.QrtzMemberCouponsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * MemberBasicServiceImpl
 *
 * @author liuqianru
 * @date 2020/8/7 15:48
 */
@Service("qrtzMemberCouponsService")
public class QrtzMemberCouponsServiceImpl extends ServiceImpl<QrtzMemberCouponsDao, QrtzMemberCouponsEntity> implements QrtzMemberCouponsService {
    @Override
    public List<QrtzMemberCouponsEntity> queryAll(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<QrtzMemberCouponsEntity> queryList() {
        return baseMapper.queryList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<QrtzMemberCouponsEntity> mbList) {
        this.saveBatch(mbList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<QrtzMemberCouponsEntity> mbList) {
        this.updateBatchById(mbList);
    }
}
