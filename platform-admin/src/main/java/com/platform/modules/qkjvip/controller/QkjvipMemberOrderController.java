/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-11 14:15:23        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderService;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.util.ExcelSelectListUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.platform.modules.util.ExportExcelUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-08-11 14:15:23
 */
@RestController
@RequestMapping("qkjvip/memberorder")
public class QkjvipMemberOrderController extends AbstractController {
    @Autowired
    private QkjvipMemberOrderService qkjvipMemberOrderService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberorder:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberOrderEntity> list = qkjvipMemberOrderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberorder:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberOrderService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberorder:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberOrderEntity qkjvipMemberOrder = qkjvipMemberOrderService.getById(id);

        return RestResponse.success().put("memberorder", qkjvipMemberOrder);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberOrder qkjvipMemberOrder
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberorder:save")
    public RestResponse save(@RequestBody QkjvipMemberOrderEntity qkjvipMemberOrder) {
        qkjvipMemberOrder.setSource(0);
        qkjvipMemberOrderService.add(qkjvipMemberOrder);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberOrder qkjvipMemberOrder
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberorder:update")
    public RestResponse update(@RequestBody QkjvipMemberOrderEntity qkjvipMemberOrder) {

        qkjvipMemberOrderService.update(qkjvipMemberOrder);

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
    @RequiresPermissions("qkjvip:memberorder:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberOrderService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 导入会员数据
     */
    @SysLog("导入订单")
    @RequestMapping("/import")
    @RequiresPermissions("qkjvip:memberorder:save")
    public RestResponse importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            try {
                List<QkjvipMemberOrderEntity> list = ExportExcelUtils.importExcel(file, 1, 1,QkjvipMemberOrderEntity.class);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSource(0);
                }
                qkjvipMemberOrderService.saveBatch(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RestResponse.success().put("msg", "导入成功！");
    }

    /**
     * 导出会员数据模板
     */
    @SysLog("导出会员模板")
    @RequestMapping("/exportTpl")
    @RequiresPermissions("qkjvip:memberorder:save")
    public void exportTplExcel(HttpServletRequest request, HttpServletResponse response) {
        List<QkjvipMemberOrderEntity> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        List<SysDictEntity> dictList = new ArrayList<>();
        String[] dictAttr = null;
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("订单表","会员订单"), QkjvipMemberOrderEntity .class, list);
            //这里是自己加的 带下拉框的代码
            ExcelSelectListUtil.selectList(workbook, 12, 12, new String[]{"是","非"});

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "会员订单信息表." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
//            ExportExcelUtils.exportExcel(list,"会员信息表","会员信息",MemberEntity.class,"会员信息",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
