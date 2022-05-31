/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitytempController.java
 * 包名称:com.platform.modules.qkjrpt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 13:05:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.controller;

import cn.emay.util.DateUtil;
import cn.emay.util.JsonHelper;
import com.alibaba.druid.util.DaemonThreadFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjrpt.entity.QkjrptReportSalesAreaEntity;
import com.platform.modules.qkjrpt.entity.QkjrptReportSalesAreaLevelEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitytempEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivitytempService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.ToolsUtil;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-09-15 13:05:30
 */
@RestController
@RequestMapping("qkjrpt/reportactivitytemp")
public class QkjrptReportActivitytempController extends AbstractController {
    @Autowired
    private QkjrptReportActivitytempService qkjrptReportActivitytempService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {

        List<QkjrptReportActivitytempEntity> list = qkjrptReportActivitytempService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllenum")
    public RestResponse queryAllenum(@RequestParam Map<String, Object> params) {

        List<QkjrptReportActivitytempEntity> list = qkjrptReportActivitytempService.queryAll(params);

        return RestResponse.success().put("list", list);
    }


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllRpt")
    public RestResponse queryAllRpt(@RequestParam Map<String, Object> params) {
        QkjrptReportActivitytempEntity rpt = new QkjrptReportActivitytempEntity();
        rpt.setListorgno(ContextHelper.getPermitDepts(""));
        List<QkjrptReportActivitytempEntity> perlist = qkjrptReportActivitytempService.queryAllRpt(params);

        String starttime = params.get("startregtime").toString();
        String endtime = params.get("endregtime").toString();
        if (params.get("starttime")!=null && !params.get("starttime").equals("")) { //首页时间不为空
//            params.put("startregtime",params.get("starttime").toString().substring(0,10));
//            params.put("endregtime",params.get("starttime").toString().substring(0,10));
            params.put("startregtime",params.get("startregtime").toString().substring(0,10));
            String nowd = DateUtils.format(new Date(), "yyyy-MM-dd");
            params.put("homestartime",nowd.substring(0,7) + "-01");
            starttime = params.get("homestartime").toString();
            params.put("acttype","1");
        }
        List<QkjrptReportActivitytempEntity> pertotal =qkjrptReportActivitytempService.queryAllRptTotal(params);
        QkjrptReportActivitytempEntity per = new QkjrptReportActivitytempEntity();
        if (pertotal.size()>0){
            per=pertotal.get(0);
            if (!starttime.substring(0, 4).equals(endtime.substring(0, 4))) {  // 同一个年
                per.setYearscale("--");
            }
            if (!starttime.substring(0, 7).equals(endtime.substring(0, 7))) {  // 同一个月
                per.setMonthscale("--");
            }
        }
        return RestResponse.success().put("perlist", perlist).put("pertotal",per);
    }

    /**
     * 查看所有列表类别
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllTypeRpt")
    public RestResponse queryAllTypeRpt(@RequestParam Map<String, Object> params) {
        String type = null;
        if (params.get("acttypeselect")!=null)type = params.get("acttypeselect")+"";
        List<QkjrptReportActivitytempEntity> perlist = new ArrayList<>();
        if (type == null || type.equals("") || type.equals("1")) {
            perlist = qkjrptReportActivitytempService.queryAllTypeRpt(params);
        } else {
            params.put("acttypesource",type);
            params.put("acttype",type); //首页查询环比。。
            perlist = qkjrptReportActivitytempService.queryAllRptSource(params);
        }

        // 环比。。查询 (人次）
        if (params.get("acttype")==null) {
            params.put("acttype","1");
        }

        if (params.get("endregtime")==null || params.get("endregtime").equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("endregtime",sdf.format(new Date()));
        }
        String starttime = params.get("startregtime").toString();
        String endtime = params.get("endregtime").toString();
        if (params.get("starttime")!=null && !params.get("starttime").equals("")) { //首页时间不为空
//            params.put("startregtime",params.get("starttime").toString().substring(0,10));
//            params.put("endregtime",params.get("starttime").toString().substring(0,10));
            String nowd = DateUtils.format(new Date(), "yyyy-MM-dd");
            params.put("homestartime",nowd.substring(0,7) + "-01");
            starttime = params.get("homestartime").toString();
        }
        List<QkjrptReportActivitytempEntity> pertotal =qkjrptReportActivitytempService.queryAllRptTotal(params);
        QkjrptReportActivitytempEntity per = new QkjrptReportActivitytempEntity();
        if (pertotal.size()>0){
            per=pertotal.get(0);
            if (!starttime.substring(0, 4).equals(endtime.substring(0, 4))) {  // 同一个年
                per.setYearscale("--");
            }
            if (!starttime.substring(0, 7).equals(endtime.substring(0, 7))) {  // 同一个月
                per.setMonthscale("--");
            }
        }
        return RestResponse.success().put("perlist", perlist).put("pertotal",per);
    }

    /**
     * 查看所有列表透视
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllRptTs")
    public RestResponse queryAllRptTs(@RequestParam Map<String, Object> params) {
        List<QkjrptReportActivitytempEntity> perlist = qkjrptReportActivitytempService.queryAllRptTs(params);
        // 环比。。查询 (人次）
        if (params.get("acttype")==null) {
            params.put("acttype","1");
        }
        if (params.get("endregtime")==null || params.get("endregtime").equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("endregtime",sdf.format(new Date()));
        }
        String starttime = params.get("startregtime").toString();
        String endtime = params.get("endregtime").toString();
        List<QkjrptReportActivitytempEntity> pertotal =qkjrptReportActivitytempService.queryAllRptTotal(params);
        QkjrptReportActivitytempEntity per = new QkjrptReportActivitytempEntity();
        if (pertotal.size()>0){
            per=pertotal.get(0);
            if (!starttime.substring(0, 4).equals(endtime.substring(0, 4))) {  // 同一个年
                per.setYearscale("--");
            }
            if (!starttime.substring(0, 7).equals(endtime.substring(0, 7))) {  // 同一个月
                per.setMonthscale("--");
            }
        }
        return RestResponse.success().put("perlist", perlist).put("pertotal",per);
    }

    /**
     * 费效比
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllRptCost")
    public RestResponse queryAllRptCost(@RequestParam Map<String, Object> params) throws IOException, ParseException {
        List<QkjrptReportActivitytempEntity> perlist = qkjrptReportActivitytempService.queryAllRpt(params);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        QkjrptReportSalesAreaEntity sae = new QkjrptReportSalesAreaEntity();
        if (params.get("startregtime")!=null&&!params.get("startregtime").equals("")) {
            sae.setStarttime(sdf.parse(params.get("startregtime")+""));
        }
        if (params.get("endregtime")!=null&&!params.get("endregtime").equals("")) {
            sae.setEndtime(sdf.parse(params.get("endregtime")+""));
        }
        if (params.get("membergroup")!=null&&!params.get("membergroup").equals("")) {
            sae.setMembergroup(params.get("membergroup")+"");
        }
        if (params.get("identitylevel")!=null&&!params.get("identitylevel").equals("")) {
            sae.setIdentitylevel((Integer) params.get("identitylevel"));
        }
        List<QkjrptReportSalesAreaEntity> list = new ArrayList<>();
        Object obj = JSONArray.toJSON(sae);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_SALE_URl, queryJsonStr);
        System.out.println("价值区间统计-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),QkjrptReportSalesAreaEntity.class);
            }
        }
        List<QkjrptReportSalesAreaEntity> aplist = new ArrayList<>();
        if (list!=null&&list.size()>0) {
            for (QkjrptReportSalesAreaEntity sa:list) {
                for (QkjrptReportActivitytempEntity act:perlist) {
                    if(act.getAreacode()!=null&&sa.getAreacode()!=null&&act.getAreacode().equals(sa.getAreacode())){
                        sa.setActivityamount(act.getTotalcost());
                        Double x = sa.getAmount()/act.getTotalcost();
                        sa.setScale(Double.parseDouble(String.format("%.2f", x)));
                    }
                }
                aplist.add(sa);
            }
        }
        return RestResponse.success().put("perlist", aplist);
    }

    /**
     * 费效比
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllRptCostTs")
    public RestResponse queryAllRptCostTs(@RequestParam Map<String, Object> params) throws ParseException, IOException {
        List<QkjrptReportActivitytempEntity> perlist = qkjrptReportActivitytempService.queryAllRptTs(params);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        QkjrptReportSalesAreaLevelEntity sae = new QkjrptReportSalesAreaLevelEntity();

        if (params.get("startregtime")!=null&&!params.get("startregtime").equals("")) {
            sae.setStarttime(sdf.parse(params.get("startregtime")+""));
        }
        if (params.get("endregtime")!=null&&!params.get("endregtime").equals("")) {
            sae.setEndtime(sdf.parse(params.get("endregtime")+""));
        }
        if (params.get("membergroup")!=null&&!params.get("membergroup").equals("")) {
            sae.setMembergroup(params.get("membergroup")+"");
        }
        if (params.get("identitylevel")!=null&&!params.get("identitylevel").equals("")) {
            sae.setIdentitylevel(params.get("identitylevel")+"");
        }
        if (params.get("areacode")!=null&&!params.get("areacode").equals("")) {
            sae.setAreacode(params.get("areacode")+"");
        }
        List<QkjrptReportSalesAreaLevelEntity> list = new ArrayList<>();
        Object obj = JSONArray.toJSON(sae);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_SALELEAVEURl, queryJsonStr);
        System.out.println("费用比办事处统计-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),QkjrptReportSalesAreaLevelEntity.class);
            }
        }

        return RestResponse.success().put("perlist", perlist).put("salelist",list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportActivitytempService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrpt:reportactivitytemp:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrptReportActivitytempEntity qkjrptReportActivitytemp = qkjrptReportActivitytempService.getById(id);

        return RestResponse.success().put("reportactivitytemp", qkjrptReportActivitytemp);
    }

    /**
     * 新增
     *
     * @param qkjrptReportActivitytemp qkjrptReportActivitytemp
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrpt:reportactivitytemp:save")
    public RestResponse save(@RequestBody QkjrptReportActivitytempEntity qkjrptReportActivitytemp) {

        qkjrptReportActivitytempService.add(qkjrptReportActivitytemp);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrptReportActivitytemp qkjrptReportActivitytemp
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrpt:reportactivitytemp:update")
    public RestResponse update(@RequestBody QkjrptReportActivitytempEntity qkjrptReportActivitytemp) {

        qkjrptReportActivitytempService.update(qkjrptReportActivitytemp);

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
    @RequiresPermissions("qkjrpt:reportactivitytemp:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrptReportActivitytempService.deleteBatch(ids);

        return RestResponse.success();
    }
}
