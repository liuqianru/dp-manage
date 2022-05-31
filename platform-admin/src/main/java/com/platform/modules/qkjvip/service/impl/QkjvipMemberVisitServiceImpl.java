/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-01 11:39:36        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.common.utils.ShiroUtils;
import com.platform.modules.qkjvip.dao.QkjvipMemberVisitDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitMaterialEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitMaterialService;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-12-01 11:39:36
 */
@Service("qkjvipMemberVisitService")
public class QkjvipMemberVisitServiceImpl extends ServiceImpl<QkjvipMemberVisitDao, QkjvipMemberVisitEntity> implements QkjvipMemberVisitService {

    @Autowired
    private QkjvipMemberVisitMaterialService qkjvipMemberVisitMaterialService;
    @Override
    public List<QkjvipMemberVisitEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipMemberVisitEntity> page = new Query<QkjvipMemberVisitEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberVisitPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberVisitEntity qkjvipMemberVisit) {
        return this.save(qkjvipMemberVisit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<QkjvipMemberVisitEntity> qkjvipMemberVisitList) {

        this.saveBatch(qkjvipMemberVisitList);
        List<QkjvipMemberVisitMaterialEntity> materialList = new ArrayList<>();
        for (QkjvipMemberVisitEntity memberVisit : qkjvipMemberVisitList) {
            if (memberVisit.getMaterialList().size() > 0) {
                for (QkjvipMemberVisitMaterialEntity visitMaterial : memberVisit.getMaterialList()) {
                    visitMaterial.setVisitId(memberVisit.getId());
                    visitMaterial.setAddUser(ShiroUtils.getUserId());
                    visitMaterial.setAddDept(ShiroUtils.getUserEntity().getOrgNo());
                    visitMaterial.setAddTime(new Date());
                    materialList.add(visitMaterial);
                }
            }
        }
        if (materialList.size() > 0) {
            qkjvipMemberVisitMaterialService.addBatch(materialList);
        }
    }

    @Override
    public boolean update(QkjvipMemberVisitEntity qkjvipMemberVisit) {
        return this.updateById(qkjvipMemberVisit);
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
    public boolean closeById(String id) { return baseMapper.closeById(id); }
}
