/*
 * 项目名称:platform-plus
 * 类名称:ActServiceController.java
 * 包名称:com.platform.modules.act.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-03-18 09:47:54        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.act.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ActServiceController
 *
 * @author 孙
 */
@RestController
public class addjsonServiceController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * stencilset
     *
     * @return stencilset.json
     */
    @RequestMapping("/addjson/address")
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getResourceAsStream("/static/map.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }


}
