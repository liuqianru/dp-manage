/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareSendrecordServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareSendrecordDao;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareSendrecordService;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberImportQueryEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Service("qkjrtsWelfareSendrecordService")
public class QkjrtsWelfareSendrecordServiceImpl extends ServiceImpl<QkjrtsWelfareSendrecordDao, QkjrtsWelfareSendrecordEntity> implements QkjrtsWelfareSendrecordService {

    @Override
    public List<QkjrtsWelfareSendrecordEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjrtsWelfareSendrecordEntity> getWelfareList(Map<String, Object> params) {
        return baseMapper.getWelfareList(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrtsWelfareSendrecordEntity> page = new Query<QkjrtsWelfareSendrecordEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfareSendrecordPage(page, params));
    }

    @Override
    public boolean add(QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord) {
        return this.save(qkjrtsWelfareSendrecord);
    }

    @Override
    public void addBatch(List<QkjrtsWelfareSendrecordEntity> sendrecordList) {
        this.saveBatch(sendrecordList, 1000);
    }

    @Override
    @Async
    public void addBatchByMember(MemberImportQueryEntity memberImportQueryEntity, List<MemberEntity> memberList) {
        if (memberList.size() > 0) {
            List<QkjrtsWelfareSendrecordEntity> list = new ArrayList<>();
            Map params = new HashMap();
            for (MemberEntity member : memberList) {
                params.clear();
                params.put("memberid", member.getMemberId());
                params.put("scenetype", 2);
                List<QkjrtsWelfareSendrecordEntity> sendrecordList = baseMapper.queryAll(params);
                if (sendrecordList.size() == 0) {
                    QkjrtsWelfareSendrecordEntity sendrecord = new QkjrtsWelfareSendrecordEntity();
                    sendrecord.setMemberid(member.getMemberId());
                    sendrecord.setWelfareid(memberImportQueryEntity.getWelfareid());
                    sendrecord.setWelfarename(memberImportQueryEntity.getWelfarename());
                    sendrecord.setScenetype(2);
                    sendrecord.setWelfaretype(8);
                    if (memberImportQueryEntity.getPeriodtype() != null && memberImportQueryEntity.getPeriodtype() == 1) { // 相对日期
                        Date startDate = DateUtils.setDate(new Date());
                        sendrecord.setStartvaliddate(startDate);
                        sendrecord.setEndvaliddate(DateUtils.addDateDays(startDate, memberImportQueryEntity.getPerioddays()));
                    } else if (memberImportQueryEntity.getPeriodtype() != null && memberImportQueryEntity.getPeriodtype() == 0) {  // 绝对日期
                        sendrecord.setStartvaliddate(memberImportQueryEntity.getStartvaliddate());
                        sendrecord.setEndvaliddate(memberImportQueryEntity.getEndvaliddate());
                    }
                    list.add(sendrecord);
                }
            }
            if (list.size() > 0) {
                this.saveBatch(list, 1000);
            }
        }
    }

    @Override
    public boolean update(QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord) {
        return this.updateById(qkjrtsWelfareSendrecord);
    }

    @Override
    public boolean setWelfareStatus(List<String> idList) {
        return baseMapper.setWelfareStatus(idList);
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
