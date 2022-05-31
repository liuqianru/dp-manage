/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareBirthdayServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-13 12:20:09        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareBirthdayDao;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareBirthdayEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareBirthdayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-04-13 12:20:09
 */
@Service("qkjrtsWelfareBirthdayService")
public class QkjrtsWelfareBirthdayServiceImpl extends ServiceImpl<QkjrtsWelfareBirthdayDao, QkjrtsWelfareBirthdayEntity> implements QkjrtsWelfareBirthdayService {

    @Override
    public List<QkjrtsWelfareBirthdayEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createtime");
        params.put("asc", false);
        Page<QkjrtsWelfareBirthdayEntity> page = new Query<QkjrtsWelfareBirthdayEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfareBirthdayPage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday) {
        updateExisted(qkjrtsWelfareBirthday);
        return this.save(qkjrtsWelfareBirthday);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday) {
        if (qkjrtsWelfareBirthday.getStatus() == 0) {
            updateExisted(qkjrtsWelfareBirthday);
        }
        return this.updateById(qkjrtsWelfareBirthday);
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

    /**
     * 如果该等级的福利配置已存在则将其置为关闭
     * @param qkjrtsWelfareBirthday
     */
    public void updateExisted (QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday) {
        Map params = new HashMap();
        params.put("memberlevel", qkjrtsWelfareBirthday.getMemberlevel());
        params.put("status", 0);
        List<QkjrtsWelfareBirthdayEntity> list = baseMapper.queryAll(params);
        if (list.size() > 0) {
            list.get(0).setStatus(1);
            this.updateById(list.get(0));
        }
    }
}
