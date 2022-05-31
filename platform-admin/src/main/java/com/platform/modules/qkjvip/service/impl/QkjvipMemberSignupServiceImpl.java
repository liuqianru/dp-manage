/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-30 09:20:27        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberSignupDao;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.qkjvip.service.QkjvipMemberSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-09-30 09:20:27
 */
@Service("qkjvipMemberSignupService")
public class QkjvipMemberSignupServiceImpl extends ServiceImpl<QkjvipMemberSignupDao, QkjvipMemberSignupEntity> implements QkjvipMemberSignupService {

    @Autowired
    private MemberService memberService;

    @Override
    public List<QkjvipMemberSignupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberSignupEntity> queryTopOne(Map<String, Object> params) {
        return baseMapper.queryTopOne(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberSignupEntity> page = new Query<QkjvipMemberSignupEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberSignupPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberSignupEntity qkjvipMemberSignup) {
        return this.save(qkjvipMemberSignup);
    }

    @Override
    public boolean update(QkjvipMemberSignupEntity qkjvipMemberSignup) {
        return this.updateById(qkjvipMemberSignup);
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
    public String supadd(String activity,String member_id,String mobile){
        String id="";
        MemberEntity mem=new MemberEntity();
        mem=memberService.getById(member_id);
        //是否存在记录
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("acitvityId",activity);
        map.put("memberid",member_id);
        List<QkjvipMemberSignupEntity> mbslist = new ArrayList<>();
        mbslist=baseMapper.queryTopOne(map);
        if(mbslist.size()<=0){//无报名
            QkjvipMemberSignupEntity m=new QkjvipMemberSignupEntity();
            m.setMemberid(member_id);
            m.setAcitvityId(activity);
            m.setPhone(mobile);
            m.setUserName(mem.getMemberName());
            m.setAddtime(new Date());
            if(mem.getSex()!=null&&(mem.getSex().equals(1) || mem.getSex().equals(2)))m.setSex(mem.getSex());
            this.add(m);
            id=m.getId();
        }else{
            id=mbslist.get(0).getId();
        }
        return  id;
    }
}
