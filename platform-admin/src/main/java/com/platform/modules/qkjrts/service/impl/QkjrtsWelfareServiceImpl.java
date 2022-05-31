/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:28        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareDao;
import com.platform.modules.qkjrts.entity.*;
import com.platform.modules.qkjrts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:28
 */
@Service("qkjrtsWelfareService")
public class QkjrtsWelfareServiceImpl extends ServiceImpl<QkjrtsWelfareDao, QkjrtsWelfareEntity> implements QkjrtsWelfareService {
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;
    @Autowired
    private QkjrtsWelfareDetailsService qkjrtsWelfareDetailsService;
    @Autowired
    private QkjrtsWelfareSendrecordService qkjrtsWelfareSendrecordService;
    @Autowired
    private QkjrtsWelfareOtherobjectsService qkjrtsWelfareOtherobjectsService;

    @Override
    public List<QkjrtsWelfareEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createtime");
        params.put("asc", false);
        Page<QkjrtsWelfareEntity> page = new Query<QkjrtsWelfareEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfarePage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(QkjrtsWelfareEntity qkjrtsWelfare) {
        this.save(qkjrtsWelfare);
        //批量保存会员
        saveBatchMember(qkjrtsWelfare.getId(), qkjrtsWelfare.getMemberlist());
        //批量新增外部对象
        List<QkjrtsWelfareOtherobjectsEntity> list = new ArrayList<>();
        list.addAll(qkjrtsWelfare.getObjectslist1());
        list.addAll(qkjrtsWelfare.getObjectslist2());
        saveBatchObjects(qkjrtsWelfare.getId(), list);
        //批量增加福利对象
        saveBatchWelfare(qkjrtsWelfare);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QkjrtsWelfareEntity qkjrtsWelfare) {
        this.updateById(qkjrtsWelfare);
        // 人员列表删除
        qkjrtsWelfareMemberService.deleteByMainId(qkjrtsWelfare.getId());
        // 人员列表重新插入
        saveBatchMember(qkjrtsWelfare.getId(), qkjrtsWelfare.getMemberlist());
        // 删除外部对象
        qkjrtsWelfareOtherobjectsService.deleteByMainId(qkjrtsWelfare.getId());
        // 批量新增外部对象
        List<QkjrtsWelfareOtherobjectsEntity> list = new ArrayList<>();
        list.addAll(qkjrtsWelfare.getObjectslist1());
        list.addAll(qkjrtsWelfare.getObjectslist2());
        saveBatchObjects(qkjrtsWelfare.getId(), list);
        // 福利列表删除
        qkjrtsWelfareDetailsService.deleteByMainId(qkjrtsWelfare.getId());
        // 重新批量插入福利
        saveBatchWelfare(qkjrtsWelfare);
    }

    @Override
    public void mdy(QkjrtsWelfareEntity qkjrtsWelfare) {
        this.updateById(qkjrtsWelfare);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        this.removeByIds(Arrays.asList(ids));
        for (int i = 0; i < ids.length; i++) {
            qkjrtsWelfareMemberService.deleteByMainId(ids[i]);
            qkjrtsWelfareDetailsService.deleteByMainId(ids[i]);
            qkjrtsWelfareOtherobjectsService.deleteByMainId(ids[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(String id) {
        // 更新发送状态和发送时间
        QkjrtsWelfareEntity qkjrtsWelfare = this.getById(id);
        qkjrtsWelfare.setSendstatus(1);
        qkjrtsWelfare.setSendtime(new Date());
        this.updateById(qkjrtsWelfare);
        Map params = new HashMap();
        params.put("mainid", id);
        List<QkjrtsWelfareMemberEntity> memberList = qkjrtsWelfareMemberService.queryAll(params);
        List<QkjrtsWelfareDetailsEntity> welfareList = qkjrtsWelfareDetailsService.queryAll(params);
        List<QkjrtsWelfareSendrecordEntity> sendrecordList = new ArrayList<>();
        for (QkjrtsWelfareMemberEntity member : memberList) {
            for (QkjrtsWelfareDetailsEntity detail : welfareList) {
                for (int i = 0; i < detail.getWelfarenum(); i++) {
                    QkjrtsWelfareSendrecordEntity sendrecord = new QkjrtsWelfareSendrecordEntity();
                    sendrecord.setMainid(id);
                    sendrecord.setWelfareid(detail.getWelfareid());
                    sendrecord.setWelfarename(detail.getWelfarename());
                    sendrecord.setWelfaretype(detail.getWelfaretype());
                    sendrecord.setIntegral(detail.getIntegral());
                    sendrecord.setRightvalue(detail.getRightvalue());
                    if (detail.getPeriodtype() != null && detail.getPeriodtype() == 1) { // 相对日期
                        Date startDate = DateUtils.setDate(new Date());
                        sendrecord.setStartvaliddate(startDate);
                        sendrecord.setEndvaliddate(DateUtils.addDateDays(startDate, detail.getPerioddays()));
                    } else if (detail.getPeriodtype() != null && detail.getPeriodtype() == 0) {  // 绝对日期
                        sendrecord.setStartvaliddate(detail.getStartvaliddate());
                        sendrecord.setEndvaliddate(detail.getEndvaliddate());
                    }
                    sendrecord.setMemberid(member.getMemberId());
                    sendrecord.setScenetype(qkjrtsWelfare.getScenetype());
                    sendrecord.setSendtime(qkjrtsWelfare.getSendtime());
                    sendrecordList.add(sendrecord);
                }
            }
        }
        if (sendrecordList.size() > 0) {
            qkjrtsWelfareSendrecordService.addBatch(sendrecordList);
        }
    }

    /**
     * 批量保存会员
     *
     * @param mainId mainId
     * @param memberlist memberlist
     * @return RestResponse
     */
    private void saveBatchMember(String mainId, List<QkjrtsWelfareMemberEntity> memberlist) {
        if (memberlist != null && memberlist.size() > 0) {
            for (QkjrtsWelfareMemberEntity member : memberlist) {
                member.setMainid(mainId);
            }
            qkjrtsWelfareMemberService.addBatch(memberlist);
        }
    }
    /**
     * 批量保存外部对象
     *
     * @param mainId mainId
     * @param objectslist objectslist
     * @return RestResponse
     */
    private void saveBatchObjects(String mainId, List<QkjrtsWelfareOtherobjectsEntity> objectslist) {
        if (objectslist != null && objectslist.size() > 0) {
            for (QkjrtsWelfareOtherobjectsEntity object : objectslist) {
                object.setMainid(mainId);
            }
            qkjrtsWelfareOtherobjectsService.addBatch(objectslist);
        }
    }
    /**
     * 批量保存福利
     *
     * @param qkjrtsWelfare qkjrtsWelfare
     * @return RestResponse
     */
    private void saveBatchWelfare(QkjrtsWelfareEntity qkjrtsWelfare) {
        String mainId = qkjrtsWelfare.getId();
        List<QkjrtsWelfareDetailsEntity> welfarelist = qkjrtsWelfare.getWelfarelist();
        // 当welfarelist大于0时，校验一下有没有积分列表，有则删除，重新加入
        if (welfarelist != null && welfarelist.size() > 0) {
            Iterator<QkjrtsWelfareDetailsEntity> it = welfarelist.iterator();
            while(it.hasNext()){
                QkjrtsWelfareDetailsEntity welfare = it.next();
                welfare.setMainid(mainId);
                if(!StringUtils.isEmpty(welfare.getWelfaretype()) && welfare.getWelfaretype() == 2){
                    it.remove();
                }
            }
        }
        // 重新将积分福利加入列表
        if (!StringUtils.isEmpty(qkjrtsWelfare.getIntegral()) && qkjrtsWelfare.getIntegral() > 0) {
            QkjrtsWelfareDetailsEntity detail = new QkjrtsWelfareDetailsEntity();
            detail.setMainid(qkjrtsWelfare.getId());
            detail.setIntegral(qkjrtsWelfare.getIntegral());
            detail.setWelfaretype(2);
            detail.setWelfarename("积分");
            welfarelist.add(detail);
        }
        qkjrtsWelfareDetailsService.addBatch(welfarelist);
    }
}
