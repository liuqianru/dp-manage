/*
 * 项目名称:platform-plus
 * 类名称:AccesstokenServiceImpl.java
 * 包名称:com.platform.modules.accesstoken.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-12-04 16:54:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.accesstoken.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.datasources.annotation.DataSource;
import com.platform.modules.accesstoken.dao.AccesstokenDao;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2019-12-04 16:54:30
 */
@Service("accesstokenService")
public class AccesstokenServiceImpl extends ServiceImpl<AccesstokenDao, AccesstokenEntity> implements AccesstokenService {

    @Override
    @DataSource(name="three")
    public List<AccesstokenEntity> queryAll(Map<String, Object> params) {

        return baseMapper.queryAll(params);
    }

}
