/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMiddleinputServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-16 09:36:36        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptReportMiddleinputDao;
import com.platform.modules.qkjrpt.entity.QkjrptReportMiddleinputEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportMiddleinputService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-11-16 09:36:36
 */
@Service("qkjrptReportMiddleinputService")
public class QkjrptReportMiddleinputServiceImpl extends ServiceImpl<QkjrptReportMiddleinputDao, QkjrptReportMiddleinputEntity> implements QkjrptReportMiddleinputService {

    @Override
    public List<QkjrptReportMiddleinputEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrptReportMiddleinputEntity> page = new Query<QkjrptReportMiddleinputEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptReportMiddleinputPage(page, params));
    }

    @Override
    public boolean add(QkjrptReportMiddleinputEntity qkjrptReportMiddleinput) {
        return this.save(qkjrptReportMiddleinput);
    }

//    @Override
//    public boolean addMiddleinput(String materialid,String mainId,String name,String number,String unit,String unitPrice,String totalPrice,String type) {
//        QkjrptReportMiddleinputEntity qkjrptReportMiddleinput = new QkjrptReportMiddleinputEntity();
//        qkjrptReportMiddleinput.setMaterialid(materialid);
//        qkjrptReportMiddleinput.setMainId(mainId);
//        return this.save(qkjrptReportMiddleinput);
//    }

    @Override
    public boolean update(QkjrptReportMiddleinputEntity qkjrptReportMiddleinput) {
        return this.updateById(qkjrptReportMiddleinput);
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
