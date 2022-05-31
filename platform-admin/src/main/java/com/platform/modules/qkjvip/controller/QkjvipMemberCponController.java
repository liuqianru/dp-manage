/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.DateUtil;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponsonEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberImportEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.qkjvip.service.QkjvipMemberCponsonService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberCponService;
import com.platform.modules.util.ExportExcelUtils;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@RestController
@RequestMapping("qkjvip/membercpon")
public class QkjvipMemberCponController extends AbstractController {
    @Autowired
    private QkjvipMemberCponService qkjvipMemberCponService;
    @Autowired
    private QkjvipMemberCponsonService qkjvipMemberCponsonService;
    @Autowired
    private MemberService memberService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membercpon:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberCponEntity> list = qkjvipMemberCponService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membercpon:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberCponService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/membercponlist")
    @RequiresPermissions("qkjvip:membercpon:list")
    public RestResponse membercponlist(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberCponService.queryPageCount(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membercpon:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberCponEntity qkjvipMemberCpon = qkjvipMemberCponService.getById(id);
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("mainId",qkjvipMemberCpon.getId());
        qkjvipMemberCpon.setSonlists(qkjvipMemberCponsonService.queryAll(map));
        return RestResponse.success().put("membercpon", qkjvipMemberCpon);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberCpon qkjvipMemberCpon
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membercpon:save")
    public RestResponse save(@RequestBody QkjvipMemberCponEntity qkjvipMemberCpon) {
        qkjvipMemberCpon.setAddUser(getUserId());
        qkjvipMemberCpon.setAddDept(getOrgNo());
        qkjvipMemberCpon.setAddTime(DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        qkjvipMemberCpon.setStatus(0);//未发放优惠券
        qkjvipMemberCponService.add(qkjvipMemberCpon);
        List<QkjvipMemberCponsonEntity> sonlists=new ArrayList<>();
        sonlists=qkjvipMemberCpon.getSonlists();
        if(sonlists.size()>0){
            List<QkjvipMemberCponsonEntity> newsonlists=new ArrayList<>();
            for(QkjvipMemberCponsonEntity m:sonlists){
                m.setMainId(qkjvipMemberCpon.getId());
                newsonlists.add(m);
            }
            qkjvipMemberCponsonService.batchAdd(newsonlists);
        }
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberCpon qkjvipMemberCpon
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membercpon:update")
    public RestResponse update(@RequestBody QkjvipMemberCponEntity qkjvipMemberCpon) {
        qkjvipMemberCponService.update(qkjvipMemberCpon);

        List<QkjvipMemberCponsonEntity> sonlists=new ArrayList<>();
        sonlists=qkjvipMemberCpon.getSonlists();
        if(sonlists.size()>0){
            //删除
            qkjvipMemberCponsonService.deleteBatchByOrder(qkjvipMemberCpon.getId());

            List<QkjvipMemberCponsonEntity> newsonlists=new ArrayList<>();
            for(QkjvipMemberCponsonEntity m:sonlists){
                m.setMainId(qkjvipMemberCpon.getId());
                newsonlists.add(m);
            }
            qkjvipMemberCponsonService.batchAdd(newsonlists);
        }


        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:membercpon:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberCponService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 获取优惠券
     *
     * @param "
     * @return RestResponse
     */
    @SysLog("获取优惠券")
    @RequestMapping("/getCpons")
    public RestResponse getCpons() throws IOException {
        String resultPost = HttpClient.sendPost(Vars.MEMBER_CPON_LIST_URl,null);
        List<QkjvipMemberCponEntity> ms=new ArrayList<>();
        ms=JSON.parseArray(resultPost,QkjvipMemberCponEntity.class);
        return RestResponse.success().put("cponlist", ms);
    }

    /**
     * 根据主键发优惠券
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/sendCpon/{id}")
    public RestResponse sendCpon(@PathVariable("id") String id)throws IOException  {
        QkjvipMemberCponEntity qkjvipMemberCpon = qkjvipMemberCponService.getById(id);
//        qkjvipMemberCpon.setStatus(1);//修改为已发放
//        qkjvipMemberCponService.update(qkjvipMemberCpon);
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("mainId",qkjvipMemberCpon.getId());
        List<QkjvipMemberCponsonEntity> sonlists=new ArrayList<>();
        sonlists = qkjvipMemberCponsonService.queryAll(map);
        String[] cols_title = new String[sonlists.size()];
        if(sonlists.size()>0){
            for(int i=0;i<sonlists.size();i++){
                cols_title[i] = new String(sonlists.get(i).getMemberId());
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("couponid", Integer.parseInt(qkjvipMemberCpon.getCponid()));
        jsonObject.put("listmemberid", cols_title);
        jsonObject.put("remark", "");
        String memberJsonStr = JsonHelper.toJsonString(jsonObject);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_CPON_SEND_URl,memberJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //成功
            qkjvipMemberCpon.setStatus(1);//修改为已发放
            qkjvipMemberCponService.update(qkjvipMemberCpon);
            return RestResponse.success();
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
        // return RestResponse.success();
    }

    /**
     * 根据主键查优惠券
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/sendCponDetail/{id}")
    public RestResponse sendCponDetail(@PathVariable("id") String id)throws IOException  {
        QkjvipMemberCponEntity qkjvipMemberCpon = qkjvipMemberCponService.getById(id);
        return RestResponse.success().put("membercpon", qkjvipMemberCpon);
    }

    /**
     * 导入会员数据
     */
    @SysLog("导入会员")
    @RequestMapping("/import")
    public RestResponse importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        List<MemberEntity> mees=new ArrayList<>();
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            //插入impont 表
            try {
                List<MemberEntity> list = ExportExcelUtils.importExcel(file, 1, 1,MemberEntity.class);
                if (list.size() > 0) {
                    //根据手机号查询会员
                    mees=memberService.queryMemByList(list);
                }
                System.out.println("导入优惠券会员条数："+list.size()+";实际查询到会员条数："+mees.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RestResponse.success().put("memberlist", mees);
    }


    /**
     * 导入会员数据
     */
    @SysLog("导入会员")
    @RequestMapping("/importphone")
    public RestResponse importExcelphone(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        StringBuffer str=new StringBuffer();
        List<MemberEntity> mees=new ArrayList<>();
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            //插入impont 表
            try {
                List<MemberEntity> list = ExportExcelUtils.importExcel(file, 1, 1,MemberEntity.class);
                for (int i = 0; i < list.size(); i++) {
                    if(i == (list.size()-1)){
                        str.append(list.get(i).getMobile());
                    }else{
                        str.append(list.get(i).getMobile()+",");
                    }

                }
                System.out.println("导入优惠券会员条数："+list.size()+";实际查询到会员条数："+mees.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RestResponse.success().put("memberlist", str);
    }
}
