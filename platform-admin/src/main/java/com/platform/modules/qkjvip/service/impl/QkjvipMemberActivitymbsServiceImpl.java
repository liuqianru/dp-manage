/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymbsServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-18 13:35:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActivitymbsDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymbsEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymbsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-09-18 13:35:24
 */
@Service("qkjvipMemberActivitymbsService")
public class QkjvipMemberActivitymbsServiceImpl extends ServiceImpl<QkjvipMemberActivitymbsDao, QkjvipMemberActivitymbsEntity> implements QkjvipMemberActivitymbsService {

    @Override
    public List<QkjvipMemberActivitymbsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberActivitymbsEntity> queryTopOne(Map<String, Object> params) {
        return baseMapper.queryTopOne(params);
    }

    @Override
    public List<QkjvipMemberActivitymbsEntity> queryAllCount(Map<String, Object> params) {
        return baseMapper.queryAllCount(params);
    }

    @Override
    public List<QkjvipMemberActivitymbsEntity> queryAllResultExcel(Map<String, Object> params) {
        return baseMapper.queryAllResultExcel(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberActivitymbsEntity> page = new Query<QkjvipMemberActivitymbsEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberActivitymbsPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public Page queryPageCount(Map<String, Object> params) {
        //排序
        params.put("sidx", "a.id");
        params.put("asc", false);
        Page<QkjvipMemberActivitymbsEntity> page = new Query<QkjvipMemberActivitymbsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActivitymbsPageCount(page, params));
    }

    @Override
    public Page queryPageByPersonal(Map<String, Object> params) {
        //排序
        params.put("sidx", "acp.addtime");
        params.put("asc", false);
        Page<QkjvipMemberActivitymbsEntity> page = new Query<QkjvipMemberActivitymbsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectPerQkjvipMemberActivityPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs) {
        return this.save(qkjvipMemberActivitymbs);
    }

    @Override
    public boolean update(QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs) {
        return this.updateById(qkjvipMemberActivitymbs);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipMemberActivitymbsEntity> qkjvipMemberActivitymbs) {
        this.saveBatch(qkjvipMemberActivitymbs, 100);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<QkjvipMemberActivitymbsEntity> qkjvipMemberActivitymbs) {
        this.updateBatchById(qkjvipMemberActivitymbs, 100);
    }
    @Override
    public int deleteBatchByOrder(String activityId) {
        return baseMapper.deleteBatchByOrder(activityId);
    }
    @Override
    public void supadd(String activity,String member_id){
        //是否存在记录
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",activity);
        map.put("memberId",member_id);
        List<QkjvipMemberActivitymbsEntity> mbslist = new ArrayList<>();
        mbslist=baseMapper.queryTopOne(map);
        if(mbslist.size()<=0){//无邀约
            QkjvipMemberActivitymbsEntity m=new QkjvipMemberActivitymbsEntity();
            m.setStatus(2);//自主报名
            m.setActivityId(activity);
            m.setMemberId(member_id);
            this.add(m);
        }
    }

    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByActivityId(String activityId) {
        return baseMapper.queryByActivityId(activityId);
    }
}
