/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareOtherobjectsServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 14:44:54        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareOtherobjectsDao;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareDetailsEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareMemberEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareOtherobjectsEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareDetailsService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareOtherobjectsService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareSendrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-04-22 14:44:54
 */
@Service("qkjrtsWelfareOtherobjectsService")
public class QkjrtsWelfareOtherobjectsServiceImpl extends ServiceImpl<QkjrtsWelfareOtherobjectsDao, QkjrtsWelfareOtherobjectsEntity> implements QkjrtsWelfareOtherobjectsService {
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;
    @Autowired
    private QkjrtsWelfareDetailsService qkjrtsWelfareDetailsService;
    @Autowired
    private QkjrtsWelfareSendrecordService qkjrtsWelfareSendrecordService;

    @Override
    public List<QkjrtsWelfareOtherobjectsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "w.CreateTime");
        params.put("asc", false);
        Page<QkjrtsWelfareOtherobjectsEntity> page = new Query<QkjrtsWelfareOtherobjectsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfareOtherobjectsPage(page, params));
    }

    @Override
    public boolean add(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects) {
        return this.save(qkjrtsWelfareOtherobjects);
    }

    @Override
    public boolean addBatch(List<QkjrtsWelfareOtherobjectsEntity> list) {
        return this.saveBatch(list, 1000);
    }

    @Override
    public boolean update(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects) {
        return this.updateById(qkjrtsWelfareOtherobjects);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteByMainId(String mainid) {
        return baseMapper.deleteByMainId(mainid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects) {
        // 更新发送状态
        if (qkjrtsWelfareOtherobjects.getMemberlist().size() < qkjrtsWelfareOtherobjects.getSendnum()) {
            qkjrtsWelfareOtherobjects.setSendstatus(1); // 部分发放
        } else {
            qkjrtsWelfareOtherobjects.setSendstatus(2); // 全部发放
        }
        qkjrtsWelfareOtherobjects.setSendtime(new Date());
        this.updateById(qkjrtsWelfareOtherobjects);

        // 人员列表重新插入
        List<QkjrtsWelfareMemberEntity> memberList = saveBatchMember(qkjrtsWelfareOtherobjects.getId(), qkjrtsWelfareOtherobjects.getMemberlist());

        Map params = new HashMap();
        params.put("mainid", qkjrtsWelfareOtherobjects.getMainid());
        List<QkjrtsWelfareDetailsEntity> welfareList = qkjrtsWelfareDetailsService.queryAll(params);
        List<QkjrtsWelfareSendrecordEntity> sendrecordList = new ArrayList<>();
        for (QkjrtsWelfareMemberEntity member : memberList) {
            for (QkjrtsWelfareDetailsEntity detail : welfareList) {
                for (int i = 0; i < detail.getWelfarenum(); i++) {
                    QkjrtsWelfareSendrecordEntity sendrecord = new QkjrtsWelfareSendrecordEntity();
                    sendrecord.setMainid(qkjrtsWelfareOtherobjects.getId());
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
                    sendrecord.setScenetype(0);
                    sendrecord.setSendtime(qkjrtsWelfareOtherobjects.getSendtime());
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
    private List<QkjrtsWelfareMemberEntity> saveBatchMember(String mainId, List<QkjrtsWelfareMemberEntity> memberlist) {
        if (memberlist == null) memberlist = new ArrayList<>();
        List<QkjrtsWelfareMemberEntity> list = new ArrayList<>();
        if (memberlist.size() > 0) {
            for (QkjrtsWelfareMemberEntity member : memberlist) {
                if (StringUtils.isEmpty(member.getId())) {
                    member.setMainid(mainId);
                    list.add(member);
                }
            }
            if (list.size() > 0) {
                qkjrtsWelfareMemberService.addBatch(list);
            }
        }
        return list;
    }
}
