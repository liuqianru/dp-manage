/*
 * 项目名称:platform-plus
 * 类名称:SysConfigServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.JedisUtil;
import com.platform.modules.app.entity.UserEntity;
import com.platform.modules.sys.entity.SysCacheEntity;
import com.platform.modules.sys.entity.redisEntity;
import com.platform.modules.sys.service.SysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 李鹏军
 */
@Service("sysCacheService")
public class SysCacheServiceImpl implements SysCacheService {
    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public List<SysCacheEntity> queryAll(Map<String, String> params) {
        SysCacheEntity sysCacheEntity;
        List<SysCacheEntity> result = new ArrayList<>();

        String pattern = params.get("pattern");
        // 获取所有key
        Set<String> keySet = jedisUtil.keys(pattern);
        for (String key : keySet) {
            sysCacheEntity = new SysCacheEntity();
            sysCacheEntity.setCacheKey(key);
            try {
                sysCacheEntity.setValue(JSONObject.toJSON(jedisUtil.get(key)).toString());
            } catch (Exception e) {
                sysCacheEntity.setValue("");
            }
            sysCacheEntity.setSeconds(jedisUtil.ttl(key));
            result.add(sysCacheEntity);
        }
        return result;
    }

    @Override
    public int deleteBatch(String[] keys) {
        for (String key : keys) {
            jedisUtil.del(key);
        }
        return keys.length;
    }

    @Override
    public void saveDictRedis (List list,String keyname,String rediskey){
//        redisEntity red = new redisEntity();
//        red.setKey(keyname);
//        red.setEntityList(list);
//        jedisUtil.setObject(rediskey,red,0);
        Map<String, Object> map = new HashMap<>();
        StringBuilder sbs=new StringBuilder();
     //对于长度大于100的进行拆分多个key
        int i = 1;
        while (i<=list.size()){
            redisEntity red = new redisEntity();
            red.setKey(keyname);
            int endstr = i+100-1;
            if (endstr>list.size()) {
                endstr = list.size();
            }
            List sublist = new ArrayList<>(list.subList(i-1,endstr));
            red.setEntityList(sublist);
            map.put(rediskey + ":" + i + "",red);
            sbs.append(i + ","); //记录有多少个键
            i += 100; // 每一百条存储一次
        }
        jedisUtil.setpipelineObject(map,rediskey+":" + 0 +"",sbs+"");
        //jedisUtil.setList(rediskey,list,0);
    }
}
