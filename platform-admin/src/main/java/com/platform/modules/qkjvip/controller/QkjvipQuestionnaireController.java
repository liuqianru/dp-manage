/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.WriterException;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireQuestionService;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireRecordService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireService;
import com.platform.modules.util.ExportExcelUtils;
import com.platform.modules.util.QRCodeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@RestController
@RequestMapping("qkjvip/questionnaire")
public class QkjvipQuestionnaireController extends AbstractController {
    @Autowired
    private QkjvipQuestionnaireService qkjvipQuestionnaireService;
    @Autowired
    private QkjvipQuestionnaireQuestionService qkjvipQuestionnaireQuestionService;
    @Autowired
    private QkjvipQuestionnaireRecordService qkjvipQuestionnaireRecordService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:questionnaire:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipQuestionnaireEntity> list = qkjvipQuestionnaireService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:questionnaire:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipQuestionnaireService.queryPage(params);

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
        QkjvipQuestionnaireEntity qkjvipQuestionnaire = qkjvipQuestionnaireService.getById(id);
        Map map = new HashMap();
        map.put("mainid", id);
        map.put("sort", "asc");
        List<QkjvipQuestionnaireQuestionEntity> questionlist = qkjvipQuestionnaireQuestionService.queryAll(map);
        qkjvipQuestionnaire.setQuestionlist(questionlist);
        for (QkjvipQuestionnaireQuestionEntity questionEntity : questionlist) {
            questionEntity.setOptionlist(StringUtils.isNotBlank(questionEntity.getQuestionoptions()) ? JSON.parseArray(questionEntity.getQuestionoptions(), QkjvipQuestionnaireQuestionOptionEntity.class) : null);
        }

        return RestResponse.success().put("questionnaire", qkjvipQuestionnaire);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/getInfo2")
    public RestResponse getInfo(@RequestParam("id") String id) {
        QkjvipQuestionnaireEntity qkjvipQuestionnaire = qkjvipQuestionnaireService.getById(id);

        return RestResponse.success().put("questionnaire", qkjvipQuestionnaire);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/getInfo")
    public RestResponse getInfo(@RequestParam("id") String id, @RequestParam("unionid") String unionid) {
        QkjvipQuestionnaireEntity qkjvipQuestionnaire = qkjvipQuestionnaireService.getById(id);
        Map map = new HashMap();
        map.put("mainid", id);
        map.put("sort", "asc");
        List<QkjvipQuestionnaireQuestionEntity> questionlist = qkjvipQuestionnaireQuestionService.queryAll(map);
        qkjvipQuestionnaire.setQuestionlist(questionlist);
        for (QkjvipQuestionnaireQuestionEntity questionEntity : questionlist) {
            questionEntity.setOptionlist(StringUtils.isNotBlank(questionEntity.getQuestionoptions()) ? JSON.parseArray(questionEntity.getQuestionoptions(), QkjvipQuestionnaireQuestionOptionEntity.class) : null);
        }
        map.put("unionid", unionid);
        List<QkjvipQuestionnaireRecordEntity> recordList = qkjvipQuestionnaireRecordService.queryOne(map);
        boolean submitflag = false;
        int totalIntegral = 0;
        if (recordList != null && recordList.size() > 0) {
            submitflag = recordList.get(0).getSubmitflag();
            totalIntegral = recordList.get(0).getIntegral();
        }

        return RestResponse.success().put("questionnaire", qkjvipQuestionnaire).put("submitflag", submitflag).put("totalIntegral", totalIntegral);
    }

    /**
     * 新增
     *
     * @param qkjvipQuestionnaire qkjvipQuestionnaire
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:questionnaire:save")
    public RestResponse save(@RequestBody QkjvipQuestionnaireEntity qkjvipQuestionnaire) {
        qkjvipQuestionnaire.setAdduser(getUserId());
        qkjvipQuestionnaire.setAddtime(new Date());
        qkjvipQuestionnaireService.add(qkjvipQuestionnaire);
        this.mdyQuestion(qkjvipQuestionnaire);
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipQuestionnaire qkjvipQuestionnaire
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:questionnaire:update")
    public RestResponse update(@RequestBody QkjvipQuestionnaireEntity qkjvipQuestionnaire) {

        qkjvipQuestionnaireService.update(qkjvipQuestionnaire);
        this.mdyQuestion(qkjvipQuestionnaire);
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
    @RequiresPermissions("qkjvip:questionnaire:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipQuestionnaireService.deleteBatch(ids);
        qkjvipQuestionnaireQuestionService.deleteByMainIds(ids);
        return RestResponse.success();
    }

    /**
     * 导出调查问卷信息
     * @param response
     * @param params
     */
    @SysLog("导出调查问卷信息")
    @RequestMapping("/export")
    @RequiresPermissions("qkjvip:questionnaire:excel")
    public void exportExcel(HttpServletResponse response,@RequestParam Map<String, Object> params) {
        try {
            System.out.println(params.get("title"));
            List<QkjvipQuestionnaireExcelEntity> list = qkjvipQuestionnaireService.ExcelList(params);
            ExportExcelUtils.exportExcel(list,"调查问卷信息表","调查问卷信息",QkjvipQuestionnaireExcelEntity.class,"调查问卷信息",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url生成二维码
     *
     * @param url url
     * @return RestResponse
     */
    @RequestMapping("/getQrCode")
    public RestResponse getQrCode(@RequestParam("url") String url) throws IOException, WriterException {
        String qrCodeUrl = "";
        qrCodeUrl = QRCodeUtil.createQrCode(URLDecoder.decode(url,"UTF-8"),90,".jpg");
        return RestResponse.success().put("qrCodeUrl", qrCodeUrl);
    }

    private void mdyQuestion(QkjvipQuestionnaireEntity qkjvipQuestionnaire) {
        qkjvipQuestionnaireQuestionService.deleteByMainId(qkjvipQuestionnaire.getId());
        List<QkjvipQuestionnaireQuestionEntity> questionlist = qkjvipQuestionnaire.getQuestionlist();
        if (questionlist.size() > 0) {
            for (QkjvipQuestionnaireQuestionEntity questionEntity : questionlist) {
                questionEntity.setMainid(qkjvipQuestionnaire.getId());
                questionEntity.setAdduser(getUserId());
                questionEntity.setAddtime(new Date());
            }
            qkjvipQuestionnaireQuestionService.addBatch(questionlist);
        }
    }

}
