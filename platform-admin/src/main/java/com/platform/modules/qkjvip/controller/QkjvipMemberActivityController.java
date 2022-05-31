/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-03 09:49:29        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.WriterException;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.oss.entity.UploadData;
import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivitytempService;
import com.platform.modules.qkjrpt.service.QkjrptReportGroupdistributeService;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysDictService;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.afterturn.easypoi.excel.entity.enmus.CellValueType.Date;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@RestController
@RequestMapping("qkjvip/memberactivity")
public class QkjvipMemberActivityController extends AbstractController {
    @Autowired
    private QkjvipMemberActivityService qkjvipMemberActivityService;
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;
    @Autowired
    private QkjvipMemberImportService qkjvipMemberImportService;
    @Autowired
    private QkjvipMemberSignupaddressService qkjvipMemberSignupaddressService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private QkjvipMemberSignupService qkjvipMemberSignupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private QkjvipMemberSignupmemberService qkjvipMemberSignupmemberService;
    @Autowired
    private SysUserChannelService sysUserChannelService;
    @Autowired
    private QkjvipMemberActivitymaterialService qkjvipMemberActivitymaterialService;
    @Autowired
    private QkjrptReportActivitytempService qkjrptReportActivitytempService;
    @Autowired
    private QkjvipMemberActivityshopService qkjvipMemberActivityshopService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QkjrptReportGroupdistributeService qkjrptReportGroupdistributeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActivityEntity> list = qkjvipMemberActivityService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有报表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllReport")
    public RestResponse queryAllReport(@RequestParam Map<String, Object> params) {
        String pagetype = params.get("pagetype")+"";
        if (pagetype!=null&&pagetype.equals("2")) { // 二级页面有权限
            params.put("listorgno",ContextHelper.getPermitDepts("qkjvip:memberactivity:list"));
            params.put("loginuser",getUserId());
           if (params.get("listorgno") !=null && params.get("listorgno").equals("-1")){
               params.remove("listorgno");
               params.remove("loginuser");
           }
        }
        List<QkjvipMemberActivityEntity> list = qkjvipMemberActivityService.queryAllReport(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllhtml")
    public RestResponse queryAllhtml(@RequestParam Map<String, Object> params) {
        //已参加的活动MemberLayer
        List<QkjvipMemberActivityEntity> list=new ArrayList<>();
        //附近的未参加的活动(公开的及邀约的)
        HttpServletRequest request = getHttpServletRequest();
        String ip = getIp(request);
        // 百度地图申请的ak
        String ak = "Ed5GYHTqRm1E5VZgHB6jm7Qz2vckMfQg";
        // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        params.put("ispri",0);
        String topClassShow = params.get("topClassShow")+"";
        if(topClassShow!=null&&(topClassShow.equals("1")||topClassShow.equals("3"))){ // 所有进行中 || 所有已结束
            params.put("topClassShow",topClassShow);
            Page page  = qkjvipMemberActivityService.queryAllSignAddress(params);
            return RestResponse.success().put("page", page);
        }else {
            //与我相关活动
            Page mypage = qkjvipMemberActivityService.queryAllSignAddressmain(params);
            return RestResponse.success().put("page", mypage);
        }
    }

    @RequestMapping("/queryAllcount")
    public RestResponse queryAllcount(@RequestParam Map<String, Object> params) {
        //已参加的活动
        List<QkjvipMemberActivityEntity> list=new ArrayList<>();
        params.put("ispri",0);
        String topClassShow = params.get("topClassShow")+"";
        Page page  = qkjvipMemberActivityService.queryAllSignAddress(params);
        Page mypage = qkjvipMemberActivityService.queryAllSignAddressmain(params);
        return RestResponse.success().put("page", page).put("pagetwo",mypage);
    }

    /**
     * 分页查询
     * 20220429修改权限  所有公开活动及自己添加及邀约添加客户的
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
//        params.put("dataScope", getDataScopeContex("qkjvip:memberactivity:list", "t.adduser", "t.adddept"));

//        String isoutsider1 = params.get("isoutsider")+"";
//        if (isoutsider1 == null || isoutsider1.equals("false")) {
//            params.put("listorgno",ContextHelper.getPermitDepts("qkjvip:memberactivity:list"));
//            params.put("loginuser",getUserId());
//            if (params.get("listorgno") !=null && params.get("listorgno").equals("-1")){
//                params.remove("listorgno");
//                params.remove("loginuser");
//            }
//        } else {
//            params.put("isoutsider1",getUser().getOrgNo()); // 外部人员
//        }
        if ("true".equals(params.get("istaizhang"))) {
            params.remove("iskol");
            params.remove("isexcel");
        } else {
            params.put("listorgno",ContextHelper.getPermitDepts("qkjvip:memberactivity:list"));
            if (params.get("listorgno") !=null && params.get("listorgno").equals("-1")){
                params.remove("listorgno");
                params.remove("loginuser");
            } else {
                params.remove("listorgno");
                params.remove("loginuser");
                params.put("isoutsideruser",getUserId());
                params.put("isoutsider1",getUser().getOrgNo()); // 外部人员
            }
            String isexcel = params.get("isexcel")+"";
            if (isexcel.equals("")) {
                params.remove("isexcel");
            }
            String islayer = params.get("islayer")+"";
            if (islayer!=null&&!islayer.equals("0")) { // 政商务活动补录情况重新判断类别
                if (isexcel!=null&&isexcel.equals("1")) { // 补录
                    params.remove("islayer");
                    params.put("noislayer",0);
                }
            }
            String iskol = params.get("iskol")+"";
            if (iskol==null || iskol.equals("0")) { // kol活动列表不查询公关赠酒活动
                params.remove("iskol");
            }
        }
        Page page = qkjvipMemberActivityService.queryPage(params);

        if ("true".equals(params.get("istaizhang"))) {
            int planNum = 0;
            int acceptNum = 0;
            int planTotalNum = 0;
            int acceptTotalNum = 0;
            List<QkjvipMemberActivityAccountEntity> list = qkjvipMemberActivityService.queryCount(params);
            acceptNum = list.get(0).getAcceptNum();
            acceptTotalNum = list.get(0).getAcceptTotalNum();
            params.put("countType", 1);
            list = qkjvipMemberActivityService.queryCount(params);
            planNum = list.get(0).getPlanNum();
            planTotalNum = list.get(0).getPlanTotalNum();
            return RestResponse.success().put("page", page).put("list", list).put("planNum", planNum).put("acceptNum", acceptNum).put("planTotalNum", planTotalNum).put("acceptTotalNum", acceptTotalNum);
        }

        return RestResponse.success().put("page", page);
    }
    @GetMapping("/listsum")
    public RestResponse listsum (@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:memberactivity:list", "t.adduser", "t.adddept"));
        Page page = qkjvipMemberActivityService.queryPageCount(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActivityEntity qkjvipMemberActivity = qkjvipMemberActivityService.getById(id);
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",id);
        qkjvipMemberActivity.setMbs(qkjvipMemberActivitymbsService.queryAll(map));
        //查询地址
        qkjvipMemberActivity.setAddresses(qkjvipMemberSignupaddressService.queryAll(map));
        //查询物料公共
        map.put("type",0);
        qkjvipMemberActivity.setMaterials(qkjvipMemberActivitymaterialService.queryAll(map));
        map.put("type",1);
        qkjvipMemberActivity.setPermaterials(qkjvipMemberActivitymaterialService.queryAll(map));

        // 查询核心店
        qkjvipMemberActivity.setShops(qkjvipMemberActivityshopService.queryAll(map));

        return RestResponse.success().put("memberactivity", qkjvipMemberActivity);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/materinfo/{id}")
    public RestResponse materinfo(@PathVariable("id") String id) {
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",id);
        map.put("type",1);
        List<QkjvipMemberActivitymaterialEntity> materials = qkjvipMemberActivitymaterialService.queryAll(map);
        return RestResponse.success().put("materials", materials);
    }

    /**
     * 根据主键查询详情
     *
     * @param params 主键
     * @return RestResponse
     */
    @RequestMapping("/infohtml")
    public RestResponse infohtml(@RequestParam Map<String, Object> params) {
        QkjvipMemberActivityEntity qkjvipMemberActivity=new QkjvipMemberActivityEntity();
        Map<String, Object> acmap=new HashMap<>();
        acmap.put("id",params.get("id").toString());
        List<QkjvipMemberActivityEntity> acs=new ArrayList<>();
        QkjvipMemberSignupEntity qsign=new QkjvipMemberSignupEntity();//报名信息
        acs=qkjvipMemberActivityService.queryAll(acmap);
        if(acs!=null&&acs.size()==1){
            qkjvipMemberActivity=acs.get(0);
        }
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",params.get("id").toString());
        List<QkjvipMemberActivitymbsEntity> mmbs = new ArrayList<>();
        if(params.get("isqiandao") == null || !params.get("isqiandao").equals("1")) { //是报名才查询详情及地址
            mmbs = qkjvipMemberActivitymbsService.queryAll(map);
            if (mmbs != null && mmbs.size() > 0) {
                qkjvipMemberActivity.setMbs(mmbs);
            }

        }
        //查询地址
        List<QkjvipMemberSignupaddressEntity> addresses = new ArrayList<>();
        addresses = qkjvipMemberSignupaddressService.queryAll(map);
        if (addresses != null && addresses.size() > 0) {
            qkjvipMemberActivity.setAddresses(addresses);
        }
        int iscanjia=0;
        int isbaoming =0;
        List<MemberEntity> list=new ArrayList<>();
        if(params.get("myopenid")!=null && !params.get("myopenid").equals("")){//查询是否参加过本活动
            logger.info("查询活动详情：openid:" + params.get("myopenid"));
            acmap.clear();
            acmap.put("openid",params.get("myopenid"));
            if(params.get("isfollow")!=null && !params.get("isfollow").equals("")){
                acmap.put("isfollow",params.get("isfollow"));
            }
            list = memberService.selectMemberByOpenid(acmap);
            if(params.get("isqiandao") == null || !params.get("isqiandao").equals("1")){ //是报名
                Map<String, Object> mapt = new HashMap<>();
                map.put("myopenid",params.get("myopenid")+"");
                map.put("acitvityId",params.get("id").toString());
                List<QkjvipMemberSignupEntity> sgs=new ArrayList<>();
                sgs=qkjvipMemberSignupService.queryAll(map);
                if(sgs.size()>0){
                    iscanjia=1;
                }
            } else { //是否签到
                Map<String, Object> mapt = new HashMap<>();
                map.put("myopenid",params.get("myopenid")+"");
                map.put("acitvityId",params.get("id").toString());
                List<QkjvipMemberSignupEntity> sgs=new ArrayList<>();
                sgs=qkjvipMemberSignupService.queryAll(map);
                if(sgs.size()>0){
                    isbaoming=1;
                    qsign=sgs.get(0);
                }

                mapt.clear();
                mapt.put("myopenid", params.get("myopenid") + "");
                mapt.put("activityId",params.get("id").toString());
                List<QkjvipMemberSignupmemberEntity> listed = qkjvipMemberSignupmemberService.queryTopOne(mapt);
                if (listed.size() > 0) {
                    iscanjia = 1;
                    logger.info("已签到");
                }
            }

        }
        if(params.get("juerumemberid")!=null && !params.get("juerumemberid").equals("")){//根据觉如memberid查询是否参加过本活动
            acmap.clear();
            acmap.put("memberId",params.get("juerumemberid"));
            if(params.get("isfollow")!=null && !params.get("isfollow").equals("")){
                acmap.put("isfollow",params.get("isfollow"));
            }
            list = memberService.selectMemberByJuruMemberid(acmap);
            acmap.clear();
            Map<String, Object> mapt = new HashMap<>();
            if(params.get("isqiandao") == null || !params.get("isqiandao").equals("1")) { //是报名
                mapt.put("memberid", params.get("juerumemberid") + "");
                mapt.put("acitvityId", params.get("id").toString());
                List<QkjvipMemberSignupEntity> sgs = new ArrayList<>();
                sgs = qkjvipMemberSignupService.queryAll(mapt);
                if (sgs.size() > 0) {
                    iscanjia = 1;
                }
            } else { //是否签到
                mapt.clear();
                mapt.put("memberid", params.get("juerumemberid") + "");
                mapt.put("acitvityId", params.get("id").toString());
                List<QkjvipMemberSignupEntity> sgs = new ArrayList<>();
                sgs = qkjvipMemberSignupService.queryAll(mapt);
                if (sgs.size() > 0) {
                    isbaoming = 1;
                    qsign=sgs.get(0);
                }
                mapt.clear();
                mapt.put("memberId", params.get("juerumemberid") + "");
                mapt.put("activityId",params.get("id").toString());
                List<QkjvipMemberSignupmemberEntity> listed = qkjvipMemberSignupmemberService.queryTopOne(mapt);
                if (listed.size() > 0) {
                    iscanjia = 1;
                }
            }
        }
        String isabove = "0"; //未超出人数限制
        String isinvite="0";//未被邀请
        if(params.get("isqiandao") == null || !params.get("isqiandao").equals("1")) { //是报名才查询详情及地址
            if (qkjvipMemberActivity != null && qkjvipMemberActivity.getIspri() != null && (qkjvipMemberActivity.getPriPerson() != null && qkjvipMemberActivity.getPriPerson() > 0)) { //的活动(人数限制)
                if (qkjvipMemberActivity.getPriPerson() != null) {
                    if (qkjvipMemberActivity.getSignper() != null) { //报名人数
                        if (qkjvipMemberActivity.getSignper() >= qkjvipMemberActivity.getPriPerson()) {//报名人数小于限制人数
                            isabove = "1";
                        }
                    }
                }
            }
        }

        if (list != null && list.size() > 0) {
            String myop = list.get(0).getMemberId();
            map.clear();
            map.put("activityId", qkjvipMemberActivity.getId());
            map.put("memberId", myop);
            List<QkjvipMemberActivitymbsEntity> mbslist = new ArrayList<>();
            mbslist = qkjvipMemberActivitymbsService.queryTopOne(map);
            if (mbslist.size() > 0) {//有邀约
                isinvite = "1";
            }
        }

        return RestResponse.success().put("memberactivity", qkjvipMemberActivity).put("istake",iscanjia).put("isabove",isabove).put("isinvite",isinvite).put("list",list).put("isbaoming",isbaoming).put("qsign",qsign);
    }

    /**
     * 读取文件流
     */
    /**
     * 预览pdf文件
     * @param fileName
     */
    @RequestMapping("/preview/{fileName}")
    public RestResponse preview(@PathVariable("fileName")  String fileName) {

        //File file = new File("D:/temp/test01/0/"+fileName);
        if (1==1){

            System.out.println(1);
        }else{
        }
        return RestResponse.success();
    }

    @RequestMapping("/getpdfview")
    public RestResponse getpdfview(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity,HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<QkjvipMemberActivityEntity> list = new ArrayList<>();
        byte[] data = null;
        String fileaddres = qkjvipMemberActivity.getActivilog();
//        File file = new File(fileaddres);
//        FileInputStream input = new FileInputStream(file);
//        data = new byte[input.available()];
//        input.read(data);
//        response.getOutputStream().write(data);
//        input.close();
        OutputStream sos = response.getOutputStream();
        try {
            URL url = new URL(fileaddres);
            HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
            // 连接指定的网络资源
            httpUrl.connect();
            BufferedInputStream input = new BufferedInputStream(httpUrl.getInputStream());
            int b;
            while ((b = input.read())!= -1){
                sos.write(b);
            }
            response.flushBuffer();
            sos.close();
            input.close();
        } catch (Exception e) {
            logger.error("pdf文件处理异常：" + e.getMessage());
        }
//        OutputStream sos;
//        try {
//            sos = response.getOutputStream();
//            URL url = new URL(file);
//            HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
//            // 连接指定的网络资源
//            httpUrl.connect();
//            BufferedInputStream input = new BufferedInputStream(httpUrl.getInputStream());
//            int b;
//            while ((b = input.read())!= -1){
//                sos.write(b);
//            }
//            response.flushBuffer();
//            sos.close();
//            input.close();
//        } catch (Exception e) {
//            logger.error("pdf文件处理异常：" + e.getMessage());
//        }

        return RestResponse.success().put("data",sos);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActivity qkjvipMemberActivity
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        //计算总费用及人均费用
        List<QkjvipMemberActivitymaterialEntity> materialses= new ArrayList<>();
        materialses = qkjvipMemberActivity.getMaterials();
        List<QkjvipMemberActivitymaterialEntity> permaterialses= new ArrayList<>();
        permaterialses = qkjvipMemberActivity.getPermaterials();

        List<QkjvipMemberActivityshopEntity> shopss= new ArrayList<>();
        shopss = qkjvipMemberActivity.getShops();
        List<QkjvipMemberActivitymbsEntity> mbs=new ArrayList<>();
        mbs=qkjvipMemberActivity.getMbs();
        List<MemberEntity> members = qkjvipMemberActivity.getMembers();
        if (materialses!=null&&materialses.size()>0) {
            //计算物料总费用及人均费用
            Double totle=0.00;
            for(QkjvipMemberActivitymaterialEntity m:materialses){
                totle += m.getTotalprice();
            }
            Double pertotle = 0.00;
            if (totle>0&&mbs!=null&&mbs.size()>0) {
                pertotle = totle/mbs.size();
            }
            qkjvipMemberActivity.setTotalcost(totle);
            qkjvipMemberActivity.setPercost(pertotle);
        }
        qkjvipMemberActivity.setAdduser(getUserId());
        qkjvipMemberActivity.setAdddept(getOrgNo());
        qkjvipMemberActivity.setAddtime(new Date());
        if (qkjvipMemberActivity==null||qkjvipMemberActivity.getIsexcel() ==null) {
            qkjvipMemberActivity.setIsexcel(0);
        }
        if (qkjvipMemberActivity==null||qkjvipMemberActivity.getInchargeuser() ==null) {
            qkjvipMemberActivity.setInchargeuser(getUserId());
            qkjvipMemberActivity.setInchargedept(getOrgNo());
            qkjvipMemberActivity.setInchargeusername(getUser().getRealName());
        }
        qkjvipMemberActivity.setStatus(0);

        qkjvipMemberActivityService.add(qkjvipMemberActivity);
        //如果签到生成二维码
        try{
            String htmlur=qkjvipMemberActivity.getHtmlurl().substring(0,qkjvipMemberActivity.getHtmlurl().indexOf("#"));
            String url= QRCodeUtil.createQrCode(htmlur+"#/signmember?activityid="+qkjvipMemberActivity.getId(),300,".jpg");
            qkjvipMemberActivity.setIssignimg(url);
            //修改二维码
            qkjvipMemberActivityService.update(qkjvipMemberActivity);
        }catch (IOException e){

        }catch (WriterException e1){

        }

        if(mbs!=null&&mbs.size()>0){
            List<QkjvipMemberActivitymbsEntity> newmemList=new ArrayList<>();
            newmemList = mbs.stream().map(item -> {
                item.setActivityId(qkjvipMemberActivity.getId());
                item.setStatus(1);//邀约
                    if (members!=null && members.size()>0) {
                        for (MemberEntity mitem : members) {
                            if (mitem != null && mitem.getMemberId() != null && mitem.getMemberId().equals(item.getMemberId())) {
                                item.setMemberjson(JSONObject.toJSON(mitem) + "");
                                break;
                            }
                        }
                    }
                return item;
            }).collect(Collectors.toList());
            qkjvipMemberActivitymbsService.batchAdd(mbs);
        }

        List<QkjvipMemberSignupaddressEntity> addresses=new ArrayList<>();
        addresses=qkjvipMemberActivity.getAddresses();
        if(addresses!=null&&addresses.size()>0){
            List<QkjvipMemberSignupaddressEntity> newList=new ArrayList<>();
            for(QkjvipMemberSignupaddressEntity m:addresses){
                m.setActivityid(qkjvipMemberActivity.getId());
                newList.add(m);
            }
            qkjvipMemberSignupaddressService.batchAdd(addresses);
        }


        if (materialses!=null&&materialses.size()>0) {
            List<QkjvipMemberActivitymaterialEntity> newList=
                    permaterialses.stream().map(item -> {
                        item.setActivityId(qkjvipMemberActivity.getId());
                        return item;
                    }).collect(Collectors.toList());
            qkjvipMemberActivitymaterialService.batchAdd(newList);
        }
        if (permaterialses!=null && permaterialses.size()>0) {
            List<QkjvipMemberActivitymaterialEntity> newList=
                    permaterialses.stream().map(item -> {
                        item.setActivityId(qkjvipMemberActivity.getId());
                        return item;
                    }).collect(Collectors.toList());
            qkjvipMemberActivitymaterialService.batchAdd(newList);
        }

        // 核心店
        if (shopss!=null && shopss.size()>0) {
            List<QkjvipMemberActivityshopEntity> sps = shopss.stream().map(item -> {
                item.setActivityId(qkjvipMemberActivity.getId());
                return item;
            }).collect(Collectors.toList());
            qkjvipMemberActivityshopService.batchAdd(shopss);
        }
        // 更新到中间表
        return RestResponse.success().put("id", qkjvipMemberActivity.getId());
    }

    // 补充报名和签到

    /**
     * 更新活动中间表
     */
//    @Async
//    public void saveDictRedis (List list,String keyname, String key){
//        sysCacheService.saveDictRedis(list,keyname,key);
//    }

    /**
     * 更新签批文件
     *
     * @param
     * @return RestResponse
     */
    @SysLog("更新签批文件")
    @RequestMapping("/updateFile")
    public RestResponse updateFile(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        qkjvipMemberActivity.setCheckstatus(1);// 已签批
        qkjvipMemberActivityService.update(qkjvipMemberActivity);

        return RestResponse.success();
    }

    /**
     * 更新签批文件
     *
     * @param
     * @return RestResponse
     */
    @SysLog("更新结案")
    @RequestMapping("/updateClose")
    public RestResponse updateClose(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        qkjvipMemberActivity.setClosestate(1);//已关闭
        qkjvipMemberActivity.setClosetime(new Date());
        qkjvipMemberActivityService.update(qkjvipMemberActivity);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActivity qkjvipMemberActivity
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        //如果签到生成二维码
        QkjvipMemberActivityEntity oldact = qkjvipMemberActivityService.getById(qkjvipMemberActivity.getId());
        if((oldact.getIssignimg()==null||oldact.getIssignimg().equals(""))&&qkjvipMemberActivity.getHtmlurl()!=null){
            try{
                String htmlur=qkjvipMemberActivity.getHtmlurl().substring(0,qkjvipMemberActivity.getHtmlurl().indexOf("#"));
                String url= QRCodeUtil.createQrCode(htmlur+"#/signmember?activityid="+qkjvipMemberActivity.getId(),300,".jpg");
                qkjvipMemberActivity.setIssignimg(url);
            }catch (IOException e){

            }catch (WriterException e1){

            }

            //System.out.println(erweima);
        }

        List<QkjvipMemberActivitymbsEntity> mbs=new ArrayList<>();
        mbs=qkjvipMemberActivity.getMbs();

        List<MemberEntity> members = qkjvipMemberActivity.getMembers();

        List<QkjvipMemberActivityshopEntity> shopss= new ArrayList<>();
        shopss = qkjvipMemberActivity.getShops();


        //计算总费用及人均费用
        List<QkjvipMemberActivitymaterialEntity> materialses= new ArrayList<>();
        materialses = qkjvipMemberActivity.getMaterials();
        List<QkjvipMemberActivitymaterialEntity> permaterialses= new ArrayList<>();
        permaterialses = qkjvipMemberActivity.getPermaterials();
        if (materialses!=null&&materialses.size()>0) {
            //计算物料总费用及人均费用
            Double totle=0.00;
            for(QkjvipMemberActivitymaterialEntity m:materialses){
                totle += m.getTotalprice();
            }
            Double pertotle = 0.00;
            if (totle>0&&mbs!=null&&mbs.size()>0) {
                pertotle = totle/mbs.size();
            }
            qkjvipMemberActivity.setTotalcost(totle);
            qkjvipMemberActivity.setPercost(pertotle);
        }
        qkjvipMemberActivityService.update(qkjvipMemberActivity);

        if(mbs!=null&&mbs.size()>0){
            //删除
            qkjvipMemberActivitymbsService.deleteBatchByOrder(qkjvipMemberActivity.getId());
            List<QkjvipMemberActivitymbsEntity> newmemList=new ArrayList<>();
            newmemList = mbs.stream().map(item -> {
                item.setActivityId(qkjvipMemberActivity.getId());
                item.setStatus(1);//邀约
                if (members!=null && members.size()>0&&(item.getMemberjson()==null || item.getMemberjson().equals(""))) {
                    for(MemberEntity mitem:members){
                        if (mitem!=null&&mitem.getMemberId()!=null&&mitem.getMemberId().equals(item.getMemberId())) {
                            System.out.println(mitem.getOrgUserid());
                            item.setMemberjson(JSONObject.toJSON(mitem)+"");
                            break;
                        }
                    }
                }
                return item;
            }).collect(Collectors.toList());
            qkjvipMemberActivitymbsService.batchAdd(newmemList);
        } else {
            //删除
            qkjvipMemberActivitymbsService.deleteBatchByOrder(qkjvipMemberActivity.getId());
        }

        List<QkjvipMemberSignupaddressEntity> addresses=new ArrayList<>();
        addresses=qkjvipMemberActivity.getAddresses();
        if(addresses!=null&&addresses.size()>0){
            //删除
            qkjvipMemberSignupaddressService.deleteBatchByOrder(qkjvipMemberActivity.getId());
            List<QkjvipMemberSignupaddressEntity> newList=new ArrayList<>();
            for(QkjvipMemberSignupaddressEntity m:addresses){
                m.setActivityid(qkjvipMemberActivity.getId());
                newList.add(m);
            }
            qkjvipMemberSignupaddressService.batchAdd(addresses);
        }


        if (materialses!=null&&materialses.size()>0) {
            //删除
            qkjvipMemberActivitymaterialService.deleteBatchByOrder(qkjvipMemberActivity.getId(),0);
            List<QkjvipMemberActivitymaterialEntity> newList=new ArrayList<>();
            for(QkjvipMemberActivitymaterialEntity m:materialses){
                m.setActivityId(qkjvipMemberActivity.getId());
                newList.add(m);
            }
            qkjvipMemberActivitymaterialService.batchAdd(materialses);
        }
        if (permaterialses!=null && permaterialses.size()>0) {
            //删除
            qkjvipMemberActivitymaterialService.deleteBatchByOrder(qkjvipMemberActivity.getId(),1);
            List<QkjvipMemberActivitymaterialEntity> newList=new ArrayList<>();
            for(QkjvipMemberActivitymaterialEntity m:permaterialses){
                m.setActivityId(qkjvipMemberActivity.getId());
                newList.add(m);
            }
            qkjvipMemberActivitymaterialService.batchAdd(permaterialses);
        }

        // 核心店
        if (shopss!=null && shopss.size()>0) {
            //删除
            qkjvipMemberActivityshopService.deleteBatchByMainId(qkjvipMemberActivity.getId());
            List<QkjvipMemberActivityshopEntity> sps = shopss.stream().map(item -> {
                item.setActivityId(qkjvipMemberActivity.getId());
                return item;
            }).collect(Collectors.toList());
            qkjvipMemberActivityshopService.batchAdd(shopss);
        }
        return RestResponse.success().put("id", qkjvipMemberActivity.getId());
    }


    /**
     * 修改
     *
     * @param qkjvipMemberActivity qkjvipMemberActivity
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/updatembs")
    public RestResponse updatembs(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        //QkjvipMemberActivityEntity oldact = qkjvipMemberActivityService.getById(qkjvipMemberActivity.getId());

        List<QkjvipMemberActivitymbsEntity> mbs=new ArrayList<>();
        mbs=qkjvipMemberActivity.getMbs();
        if(mbs!=null&&mbs.size()>0){
            List<QkjvipMemberActivitymbsEntity> newmemList=new ArrayList<>();
            for(QkjvipMemberActivitymbsEntity m:mbs){
                //判断是否有此会员邀约
                //是否存在记录
                Map<String, Object> map=new HashMap<String,Object>();
                map.put("activityId",qkjvipMemberActivity.getId());
                map.put("memberId",m.getMemberId());
                List<QkjvipMemberActivitymbsEntity> mbslist = new ArrayList<>();
                mbslist=qkjvipMemberActivitymbsService.queryTopOne(map);
                if(mbslist.size()<=0){//无邀约
                    m.setActivityId(qkjvipMemberActivity.getId());
                    m.setStatus(3);//现场
                    newmemList.add(m);
                    qkjvipMemberActivitymbsService.add(m);
                }
                if (qkjvipMemberActivity.getIsbackqd()!=null && qkjvipMemberActivity.getIsbackqd()==1) {
                    // 补报名
                    String bmid=qkjvipMemberSignupService.supadd(qkjvipMemberActivity.getId(),m.getMemberId(),"");
                    //签到
                    Date date=new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date2=sdf.format(date);
                    Map<String, Object> params=new HashMap<String,Object>();
                    params.put("memberId",m.getMemberId());
                    params.put("activityId",qkjvipMemberActivity.getId());
                    List<QkjvipMemberSignupmemberEntity> list = qkjvipMemberSignupmemberService.queryTopOne(params);
                    if(list.size()>0) {//已签到
                    } else {
                        QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember=new QkjvipMemberSignupmemberEntity();
                        qkjvipMemberSignupmember.setMemberId(m.getMemberId());
                        qkjvipMemberSignupmember.setTime(date2);
                        qkjvipMemberSignupmember.setSignupId(bmid);
                        qkjvipMemberSignupmember.setActivityId(qkjvipMemberActivity.getId());
                        qkjvipMemberSignupmemberService.add(qkjvipMemberSignupmember);
                    }
                }

            }
            //qkjvipMemberActivitymbsService.batchAdd(mbs);

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
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActivityService.deleteBatch(ids);

        return RestResponse.success();
    }

    @SysLog("发短信")
    @RequestMapping("/sendMsg")
    public RestResponse sendMsg(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        String url=qkjvipMemberActivity.getUrl()+qkjvipMemberActivity.getId();
        //查询所有邀约人员
        List<QkjvipMemberActivitymbsEntity> mbs=new ArrayList<>();
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",qkjvipMemberActivity.getId());
        mbs=qkjvipMemberActivitymbsService.queryAll(map);
        if(mbs.size()>0){
            //发短信
            SysSmsLogEntity smsLog=new SysSmsLogEntity();
            smsLog.setContent(qkjvipMemberActivity.getMsgcontent()+"戳我直达："+url);
            //smsLog.setMobile(a.getMobile());
            smsLog.setMobile("18810242427");
            SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSms(smsLog);
//            for(QkjvipMemberActivitymbsEntity a:mbs){
//                if(a!=null&&a.getMobile()!=null&&!a.getMobile().equals("")){
//                    //发短信
//                    SysSmsLogEntity smsLog=new SysSmsLogEntity();
//                    smsLog.setContent(qkjvipMemberActivity.getMsgcontent()+"戳我直达："+url);
//                    //smsLog.setMobile(a.getMobile());
//                    smsLog.setMobile("18810242427");
//                    SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSms(smsLog);
//                }
//            }
        }
        return RestResponse.success();
    }

    @GetMapping("/permChannelList")
    public RestResponse permChannelList(@RequestParam Map<String, Object> params) {
        String channelIds = "";
        channelIds = sysUserChannelService.queryChannelIdByUserId(getUserId());
        params.put("queryPermission", "all");
        if ("0".equals(channelIds) || getUser().getUserName().contains("admin")) {

        } else{
            params.put("userId", getUserId());
        }

        params.put("iscore",1);
        List<SysUserChannelEntity> permChannelList = sysUserChannelService.queryPermissionChannels(params);

        return RestResponse.success().put("list", permChannelList);
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
                List<QkjvipMemberImportEntity> list = ExportExcelUtils.importExcel(file, 1, 2,QkjvipMemberImportEntity.class);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setAddUser(getUserId());
                    list.get(i).setAddDept(getOrgNo());
                    list.get(i).setAddTime(new Date());
                    list.get(i).setOfflineflag(1);
                }
                if (list.size() > 0) {
                    qkjvipMemberImportService.addBatch(list); //批量导入临时表
                    long start, end2;
                    start = System.currentTimeMillis();
                    //调用数据清洗接口
                    Object objList = JSONArray.toJSON(list);
                    String memberJsonStr = JsonHelper.toJsonString(objList, "yyyy-MM-dd HH:mm:ss");
                    String resultPost = HttpClient.sendPost(Vars.MEMBER_IMPORT_URL, memberJsonStr);

                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if (!"200".equals(resultObject.get("resultcode").toString())) {  //清洗失败
                        return RestResponse.error(resultObject.get("descr").toString());
                    }else {
                        mees=JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
                    }
                    end2 = System.currentTimeMillis();
                    System.out.println("批量处理n条数据，耗费了" + (end2 - start) + "ms");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RestResponse.success().put("memberlist", mees);
    }

    /**
     * 导出普通会员数据模板
     */
    @SysLog("导出礼品模板")
    @RequestMapping("/exportTpl")
    public void exportTplExcel(String id, HttpServletResponse response) {
        QkjvipMemberActivityEntity qkjvipMemberActivity = qkjvipMemberActivityService.getById(id);
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("activityId",id);
        map.put("exportTpl", "1");// 导出

        // 字段列表
        List<SysDictEntity> dictList = new ArrayList<>();
        Map params = new HashMap();
        params.put("status", 1);
        params.put("selectPt", "true");
        dictList = sysDictService.queryAll(params);

        // 核心店/团购经销商列表
        params.clear();
        List<QkjrptReportGroupdistributeEntity> distributeList = new ArrayList<>();
        distributeList = qkjrptReportGroupdistributeService.queryAll(params);

        List<QkjvipMemberActivitymbsEntity> mbs=qkjvipMemberActivitymbsService.queryAll(map);
        List<QkjvipMemberActivityGiftEntity> list = new ArrayList<>();
        for (QkjvipMemberActivitymbsEntity item: mbs) {
            QkjvipMemberActivityGiftEntity gf = JSON.parseObject(JSON.toJSONString(item), QkjvipMemberActivityGiftEntity.class);
            gf.setTitle(qkjvipMemberActivity.getTitle());
            gf.setRecestate(0);
            if (item.getMemberjson()!=null && !item.getMemberjson().equals("")) {
                JSONObject resultObject = JSON.parseObject(item.getMemberjson());
                String indetigroup =resultObject.get("identitygroup")+"";
                String leave=resultObject.get("identitylevel")+"";
                String one=resultObject.get("areaone")+"";
                String two=resultObject.get("areatwo")+"";
                String three=resultObject.get("areathree")+"";
                String onename = "";
                String twoname = "";
                String threename = "";
                String grouporg=resultObject.get("grouporg")+"";
                String orgUsername = resultObject.get("orgUsername")+"";
                String orguserid=resultObject.get("orgUserid")+"";
                String distributeid = resultObject.get("distributeid")+""; // 团购商id
                String nation = resultObject.get("nation")+""; // 民族

                if (distributeList != null && distributeList.size() > 0 && distributeid!=null&&!distributeid.equals("")) {
                    List<QkjrptReportGroupdistributeEntity> dbe = distributeList.stream().filter(ditem -> ditem.getId().equals(distributeid)).collect(Collectors.toList());
                    if (dbe != null && dbe.size() > 0)gf.setOrgname(dbe.get(0).getDistributername());
                }
                String sex1 = resultObject.get("sex")+"";
                gf.setSex(sex1.equals("1") ? "男" : sex1.equals("2") ? "女" : "未知" );
                Map userMap = new HashMap();
                userMap.put("userId", orguserid);
                List<SysUserEntity> userlist = sysUserService.queryAll(userMap);
                gf.setOrgusername(orgUsername);
                if(userlist!=null&&userlist.size()>0)gf.setOrgusermobile(userlist.get(0).getMobile());

                if (dictList!=null&&dictList.size()>0) {
                    List<SysDictEntity> groups = dictList.stream().filter(groupitem -> groupitem.getCode().equals("IDENTITYGROUP") && groupitem.getValue().equals(indetigroup)).collect(Collectors.toList());
                    gf.setIdentitygroupname(groups!=null&&groups.size()>0?groups.get(0).getName():"");

                    List<SysDictEntity> leaves = dictList.stream().filter(groupitem -> groupitem.getCode().equals("IDENTITYLEVEL") && groupitem.getValue().equals(leave)).collect(Collectors.toList());
                    gf.setIdentitylevelname(leaves!=null&&leaves.size()>0?leaves.get(0).getName():"");

                    List<SysDictEntity> ones = dictList.stream().filter(groupitem -> groupitem.getCode().equals("AREAONE") && groupitem.getValue().equals(one)).collect(Collectors.toList());
                    onename = ones!=null&&ones.size()>0?ones.get(0).getName():"";

                    List<SysDictEntity> twos = dictList.stream().filter(groupitem -> groupitem.getCode().equals("AREATWO") && groupitem.getValue().equals(two)).collect(Collectors.toList());
                    twoname = twos!=null&&twos.size()>0?twos.get(0).getName():"";

                    List<SysDictEntity> thresses = dictList.stream().filter(groupitem -> groupitem.getCode().equals("AREATHREE") && groupitem.getValue().equals(three)).collect(Collectors.toList());
                    threename = thresses!=null&&thresses.size()>0?thresses.get(0).getName():"";
                    gf.setArea(onename+"/"+twoname+"/"+threename);

                    List<SysDictEntity> ggs = dictList.stream().filter(groupitem -> groupitem.getCode().equals("GROUPORG") && groupitem.getValue().equals(grouporg)).collect(Collectors.toList());
                    gf.setGrouporgname(ggs!=null&&ggs.size()>0?ggs.get(0).getName():"");

                    List<SysDictEntity> nations = dictList.stream().filter(groupitem -> groupitem.getCode().equals("NATION") && groupitem.getValue().equals(nation)).collect(Collectors.toList());
                    gf.setNationname(nations!=null&&nations.size()>0?nations.get(0).getName():"");

                }
            }

            list.add(gf);
        }


        String[] dictAttr = null;
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("礼品信息表", "礼品信息"), QkjvipMemberActivityGiftEntity.class, list);
            Sheet sheet = workbook.getSheet("礼品信息");
            //获取第三行
            Row titlerow = sheet.getRow(1);
            //有多少列
            int cellNum = titlerow.getLastCellNum();
            for (int k = 0; k < cellNum; k++) {
                String content = "";
                //根据索引获取对应的列
                Cell cell = titlerow.getCell(k);
                String cellTitle = cell != null? cell.toString() : "";
                if (cell != null && !"".equals(cell.toString())) {
                    switch (cellTitle) {
                        case "单位名称":
                        case "会员名称":
                        case "会员手机号":
                        case "职务":
                        case "收货地址-省":
                        case "收货地址-市":
                        case "收货地址-区县":
                        case "收货地址":
                        case "身份类型":
                        case "身份等级":
                        case "区域":
                        case "介绍人":
                        case "介绍人手机":
                        case "获客渠道":
                        case "维护人":
                        case "维护人手机":
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "核心店/团购经销商":
                        case "性别":
                        case "民族":
                            ExcelUtil.setStylesBlue(cell, workbook);
                            break;
                        case "接收状态":
                            ExcelSelectListUtil.selectList(workbook, k, k, new String[]{"已接收","拒绝","未知"});
                            break;
                        case "隐藏列":
                            ExcelUtil.setStylesNoEdit(cell, workbook);
                            break;
                        default:
                            break;
                    }
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "礼品信息表." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入政商务会员数据
     */
    @SysLog("导入")
    @RequestMapping("/importgift")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse importgift(String actid,MultipartFile file, UploadData uploadData) {
        String fileName = file.getOriginalFilename();
        String batchno = "";  // 批次号

        if (uploadData == null) uploadData = new UploadData();
        List<QkjvipMemberActivitymaterialEntity> permaterialses= new ArrayList<>();
        List<QkjvipMemberActivitymbsEntity> mbs=new ArrayList<>();
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            try {
                List<QkjvipMemberActivityGiftEntity> list = ExportExcelUtils.importExcel(file, 1, 1,QkjvipMemberActivityGiftEntity.class);
                Map<String, Integer> phonemap=new HashMap<String,Integer>();
                if (list.size() > 0) {
                    batchno = UUID.randomUUID().toString().replaceAll("-", "");
                    // 判断手机号是否都存在
                    StringBuffer sb = new StringBuffer();
                    list.stream().forEach(item -> {
                        if(item.getMobile()!=null&&!item.getMobile().equals(""))sb.append(item.getMobile()).append(",");
                    });
                    Map<String, String> mobilemap=new HashMap<String,String>();
                    String mobis = sb.deleteCharAt(sb.length()-1)+"";
                    mobilemap.put("mobiles",mobis);
                    String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_MOBILE_URL, JsonHelper.toJsonString(mobilemap, "yyyy-MM-dd HH:mm:ss"));
                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                        JSONArray notExistsMobiles = JSONArray.parseArray(resultObject.getString("notExistsMobiles"));
                        if(notExistsMobiles.size()>0) {
                            return RestResponse.error(notExistsMobiles+"手机号为新增客户,请先在客户列表添加客户后重新上传！");
                        }
                    } else {
                        return RestResponse.error(resultObject.get("descr").toString());
                    }
                    // 根据手机号查询所有会员信息
                    mobilemap.clear();
                    mobilemap.put("mobile",mobis);
                    resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, JsonHelper.toJsonString(mobilemap, "yyyy-MM-dd HH:mm:ss"));
                    resultObject = JSON.parseObject(resultPost);
                    List<MemberEntity> memberall=new ArrayList<>();
                    if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                        memberall=JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
                    } else {
                        return RestResponse.error(resultObject.get("descr").toString());
                    }

                    // 邀约
                    Map<String, Object> map=new HashMap<String,Object>();
                    map.put("activityId",actid);
                    mbs=qkjvipMemberActivitymbsService.queryAll(map); // 邀请列表

                    List<QkjvipMemberActivitymbsEntity> addmbs= new ArrayList<>();//需要增加邀约的记录
                    // 增加礼品
                    for (int i = 0; i < list.size(); i++) {
                        String mobile=list.get(i).getMobile();
                        int rownum = i + 3;
                        if (mobile==null||mobile.equals("")) {
                            return RestResponse.error("第" + rownum + "行手机号为空,请修改后重新上传！");
                        }
                        if (list.get(i).getRecestate()==null || list.get(i).getRecestate().equals("")) {
                            return RestResponse.error("第" + rownum + "行接收状态为空,请修改后重新上传！");
                        }
                        if(phonemap.containsKey(mobile)) {
                            return RestResponse.error("第" + rownum + "行手机号重复,请修改后重新上传！");
                        } else {
                            phonemap.put(mobile,1);
                        }
                        List<QkjvipMemberActivitymbsEntity> existmbs=mbs.stream().filter(item->item.getMobile()!=null&&item.getMobile().equals(mobile)).collect(Collectors.toList());
                        List<MemberEntity> memberalladd = memberall.stream().filter(item ->item.getMobile()!=null&&item.getMobile().equals(mobile)).collect(Collectors.toList());
                        if(memberalladd!=null&&memberalladd.size()>0) {
                            if(existmbs==null||existmbs.size()<=0){ // 手机号不在邀约中
                                QkjvipMemberActivitymbsEntity addmbsbean = new QkjvipMemberActivitymbsEntity();
                                addmbsbean.setActivityId(actid);
                                addmbsbean.setStatus(1);
                                addmbsbean.setMemberId(memberalladd.get(0).getMemberId());
                                addmbsbean.setJobTitle(memberalladd.get(0).getJobTitle());
                                addmbsbean.setMemberjson(JsonHelper.toJsonString(memberalladd.get(0)));
                                addmbsbean.setMobile(memberalladd.get(0).getMobile());
                                addmbsbean.setCompanyName(memberalladd.get(0).getCompanyName());
                                addmbsbean.setMemberName(memberalladd.get(0).getMemberName()!=null?memberalladd.get(0).getMemberName():memberalladd.get(0).getRealName());
                                addmbs.add(addmbsbean);
                                mbs.add(addmbsbean);
                            }
                            QkjvipMemberActivitymaterialEntity me = new QkjvipMemberActivitymaterialEntity();
                            me.setActivityId(actid);
                            me.setName("套餐A");
                            me.setNumber(1.00);
                            me.setType(1); // 个人礼品
                            me.setMemberid(memberalladd.get(0).getMemberId());
                            me.setRecestate(list.get(i).getRecestate());
                            me.setReceremark(list.get(i).getReceremark());
                            me.setAddress((list.get(i).getAddress()==null||list.get(i).getAddress().equals(""))? memberalladd.get(0).getAddress():list.get(i).getAddress());
                            me.setJobTitle((list.get(i).getJobTitle()==null||list.get(i).getJobTitle().equals("")) ? memberalladd.get(0).getJobTitle():list.get(i).getJobTitle());
                            permaterialses.add(me);
                        }
                    }
                    if (addmbs!=null&&addmbs.size()>0) {
                        qkjvipMemberActivitymbsService.batchAdd(addmbs);
                    }
                    if (permaterialses!=null && permaterialses.size()>0) {
                        //删除
                        qkjvipMemberActivitymaterialService.deleteBatchByOrder(actid,1);
                        qkjvipMemberActivitymaterialService.batchAdd(permaterialses);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return RestResponse.error("导入接口异常！");
            }
        }
        return RestResponse.success().put("msg", "导入成功！").put("batchno", batchno).put("permaterialses",permaterialses).put("mbs",mbs);
    }


    @SysLog("更新会员")
    @RequestMapping("/importmember")
    public RestResponse importmember(@RequestBody QkjvipMemberActivityEntity qkjvipMemberActivity) {
        List<QkjvipMemberActivitymaterialEntity> permaterialses = qkjvipMemberActivity.getPermaterials();
        if (permaterialses!=null && permaterialses.size()>0) {
            permaterialses.stream().forEach(item -> {
                MemberEntity member = new MemberEntity();
                member.setMemberId(item.getMemberid());
                if(item.getAddress()!=null&&!item.getAddress().equals(""))member.setAddress(item.getAddress());
                if(item.getJobTitle()!=null&&!item.getJobTitle().equals(""))member.setJobTitle(item.getJobTitle());
                member.setCurrentmemberid(getUserId());  // 当前登录人id，接口判断是否主业务员用
                try {
                    Object obj = JSONArray.toJSON(member);
                    String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                    String resultPost = HttpClient.sendPost(Vars.MEMBER_UPDATE_URL, memberJsonStr);
                    System.out.println("会员信息更新：" + memberJsonStr);
                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if (!"200".equals(resultObject.get("resultcode").toString())) {  //修改不成功
                        System.out.println("会员地址更新失败");
                    }
                } catch (IOException e) {
                    System.out.println("会员地址更新失败1");
                }
            });
        }
        return RestResponse.success();
    }
    /**
     * 查看活动是否对存在to韩洁
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/actityisexist")
    public RestResponse actityisexist() {
        List<QkjvipMemberActivityEntity> list = qkjvipMemberActivityService.actityisexist();
        String oneflag = "";
        String twoflag = "";
        String threeflag = "";
        List<QkjvipIsExitEntity> islist =new ArrayList<>();
        for (QkjvipMemberActivityEntity ae : list) {
            if(ae!=null&&ae.getAtype()!=null&&ae.getAtype().equals("1")){ //品鉴会
                oneflag = ae.getId();
                break;
            }
        }

        for (QkjvipMemberActivityEntity ae : list) {
            if(ae!=null&&ae.getAtype()!=null&&ae.getAtype().equals("4")){ //回场游
                twoflag = ae.getId();
                break;
            }
        }

        for (QkjvipMemberActivityEntity ae : list) {
            if(ae!=null&&ae.getAtype()!=null&&ae.getAtype().equals("6")){ //新品试饮
                threeflag = ae.getId();
                break;
            }
        }
        QkjvipIsExitEntity isex= new QkjvipIsExitEntity();
        isex.setAtype("1");
        String surl="";
        if (oneflag!=null&&!oneflag.equals("")){//有单据
            surl = "/components/newWebview/webview?name=" + URLEncoder.encode("详情") + "&url=" + URLEncoder.encode("https://crm.qkj.com.cn/#/activity?id=" + oneflag);
            isex.setHtmlurl(surl);
        }
        islist.add(isex);

        isex= new QkjvipIsExitEntity();
        isex.setAtype("4");
        surl="";
        if (twoflag!=null&&!twoflag.equals("")){//有单据
            surl = "/components/newWebview/webview?name=" + URLEncoder.encode("详情") + "&url=" + URLEncoder.encode("https://crm.qkj.com.cn/#/activity?id=" + twoflag);
            isex.setHtmlurl(surl);
        }
        islist.add(isex);

        isex= new QkjvipIsExitEntity();
        isex.setAtype("6");
        surl="";
        if (threeflag!=null&&!threeflag.equals("")){//有单据
            surl = "/components/newWebview/webview?name=" + URLEncoder.encode("详情") + "&url=" + URLEncoder.encode("https://crm.qkj.com.cn/#/activity?id=" + threeflag);
            isex.setHtmlurl(surl);
        }
        islist.add(isex);

        return RestResponse.success().put("actitylist", islist);
    }

    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 百度获取城市信息
     *
     * @param
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static void main(String[] args) throws JSONException, IOException {
//        HttpServletRequest request = getHttpServletRequest();
//        String ip = getIp(request);
//        // 百度地图申请的ak
//        String ak = "Ed5GYHTqRm1E5VZgHB6jm7Qz2vckMfQg";
//        // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
//        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + ak);
//
//        //这里只取出了两个参数，根据自己需求去获取
//        JSONObject obj = (JSONObject) ((JSONObject) json.get("content")).get("address_detail");
//        String province = obj.getString("province");
//        System.out.println(province);
//
//        JSONObject obj2 = (JSONObject) json.get("content");
//        String address = obj2.getString("address");
//        System.out.println(address);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    /**
     * 创建链接
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * 导出会员数据
     */
    @SysLog("导出活动结果")
    @RequestMapping("/export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        try {
            params.put("istoexcel","1");
            List<QkjvipMemberActivityEntity> list=qkjvipMemberActivityService.queryAll(params);
            ExportExcelUtils.exportExcel(list,"活动信息表","活动信息",QkjvipMemberActivityEntity.class,"活动信息",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
