/*
 * 项目名称:platform-plus
 * 类名称:NewsActionController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/3/11 13:09            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NewsActionController
 *
 * @author liuqianru
 * @date 2021/3/11 13:09
 */
@RestController
@RequestMapping("/remote/news")
public class NewsActionController extends AbstractController {
    /**
     * 新闻列表
     *
     * @param newsQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/newsList")
    public RestResponse list(@RequestBody NewsQueryEntity newsQueryEntity) throws IOException {
        List<NewsEntity> list = new ArrayList<>();
        Object obj = JSONArray.toJSON(newsQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.NEWS_LIST_URl, queryJsonStr);
        System.out.println("新闻列表检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            list = JSON.parseArray(resultObject.getString("newslist"), NewsEntity.class);
            Map page = new HashMap<>();
            page.put("newslist", list);
            page.put("totalcount", Long.parseLong(resultObject.get("totalcount").toString()));
            return RestResponse.success().put("page", page);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 新闻详情
     *
     * @param newsInfoQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/newsInfo")
    public RestResponse info(@RequestBody NewsInfoQueryEntity newsInfoQueryEntity) throws IOException {
        Object obj = JSONArray.toJSON(newsInfoQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.NEWS_INFO_URl, queryJsonStr);
        System.out.println("新闻详情检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            Map page = new HashMap<>();
            page.put("title", resultObject.get("title").toString());
            page.put("htmldoc", resultObject.get("htmldoc").toString());
            page.put("module", resultObject.get("module").toString());
            page.put("createtime", resultObject.get("createtime").toString());
            page.put("readnum", Integer.parseInt(resultObject.get("readnum").toString()));
            page.put("origin", resultObject.get("origin").toString());
            return RestResponse.success().put("page", page);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 热门兑换
     *
     * @param productEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/productList")
    public RestResponse productList(@RequestBody ProductEntity productEntity) throws IOException {
        String resultPost = HttpClient.sendPost(Vars.PRODUCT_LIST_URL, null);
        List<ProductEntity> productList = new ArrayList<>();
        if (StringUtils.isNotBlank(resultPost)) {
            productList = JSON.parseArray(resultPost, ProductEntity.class);
        }
        return RestResponse.success().put("productlist", productList);
    }
}
