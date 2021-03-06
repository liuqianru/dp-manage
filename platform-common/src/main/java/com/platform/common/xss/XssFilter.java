/*
 * 项目名称:platform-plus
 * 类名称:XssFilter.java
 * 包名称:com.platform.common.xss
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.common.xss;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * XSS过滤
 *
 * @author 李鹏军
 */
@Slf4j
@WebFilter(urlPatterns = "/*",filterName = "XssFilter")
public class XssFilter implements Filter {
    protected Logger logger = log;
    private String[] excludedPageArray = new String[]{"/service/model/"};

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);

        boolean isExcludedPage = false;
        //判断是否在过滤url之外
        for (String page : excludedPageArray) {
            if (((HttpServletRequest) request).getServletPath().indexOf(page) > -1) {
                isExcludedPage = true;
                break;
            }
        }
        //排除过滤url
        long startTime = System.currentTimeMillis();
        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(xssRequest, response);
        }
        long endTime = System.currentTimeMillis();
        DecimalFormat df=new DecimalFormat("0.000");
        String path = xssRequest.getRequestURI();
        logger.info(path + " "+ df.format((float)(endTime-startTime)/1000) +"m ");
    }

    @Override
    public void destroy() {
    }

}
