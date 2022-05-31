/*
 * 项目名称:platform-plus
 * 类名称:AccesstokenService.java
 * 包名称:com.platform.modules.accesstoken.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-12-04 16:54:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.accesstoken.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 孙珊珊
 * @date 2019-12-04 16:54:30
 */
public interface AccesstokenService extends IService<AccesstokenEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AccesstokenEntity> queryAll(Map<String, Object> params);


}
