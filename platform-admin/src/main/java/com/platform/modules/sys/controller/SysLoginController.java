/*
 * 项目名称:platform-plus
 * 类名称:SysLoginController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.controller;

import cn.emay.util.JsonHelper;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserRoleEntity;
import com.platform.modules.sys.form.SysLoginBindForm;
import com.platform.modules.sys.form.SysLoginForm;
import com.platform.modules.sys.service.SysCaptchaService;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.service.SysUserTokenService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录相关
 *
 * @author 李鹏军
 */
@RestController
public class SysLoginController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 验证码
     *
     * @param response response
     * @param uuid     uuid
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     *
     * @param form 登录表单
     * @return RestResponse
     */
    @SysLog("登录")
    @PostMapping("/sys/login")
    public RestResponse login(@RequestBody SysLoginForm form) {
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return RestResponse.error("验证码不正确");
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUserName());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return RestResponse.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return RestResponse.error("账号已被锁定,请联系管理员");
        }

        // 判断该账户是否已开通登录
        if (!(user.getCreateState() != null && user.getCreateState() == 1)) {
            return RestResponse.error("该账户还未开通登录权限，请联系管理员！");
        }

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(user.getUserId());

        return RestResponse.success().put("token", token).put("expire", Constant.EXPIRE).put("userMsg",user);
    }

    /**
     * 登录
     *
     * @param form 登录表单
     * @return RestResponse
     */
    @SysLog("公众号登录")
    @PostMapping("/sys/checkuser")
    public RestResponse checkuser(@RequestBody SysLoginBindForm form) throws IOException {
        //用户信息
        SysUserEntity user = sysUserService.queryByOpenid(form.getOpenid());

        //账号不存在
        if (user == null) {
            return RestResponse.error("账号未绑定，请绑定账号！");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return RestResponse.error("账号已被锁定,请联系管理员");
        }

        // 判断该账户是否已开通登录
        if (!(user.getCreateState() != null && user.getCreateState() == 1)) {
            return RestResponse.error("该账户还未开通登录权限，请联系管理员！");
        }

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(user.getUserId());

        // 判断是否是初始密码
        boolean loginMdyPwd = false;
        if (user.getPassword().equals(new Sha256Hash("888888", user.getSalt()).toHex())) {
            loginMdyPwd = true;
        }

        // 给用户打微信标签
        setUserLabel(form.getOpenid());

        return RestResponse.success().put("token", token).put("expire", Constant.EXPIRE).put("userMsg",user).put("loginMdyPwd", loginMdyPwd);
    }

    /**
     * 登录
     *
     * @param form 登录表单
     * @return RestResponse
     */
    @SysLog("绑定openid")
    @PostMapping("/sys/loginbind")
    public RestResponse loginbind(@RequestBody SysLoginBindForm form) throws IOException {
        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUserName());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return RestResponse.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return RestResponse.error("账号已被锁定,请联系管理员");
        }

        // 判断该账户是否已开通登录
        if (!(user.getCreateState() != null && user.getCreateState() == 1)) {
            return RestResponse.error("该账户还未开通登录权限，请联系管理员！");
        }

        //保存业务员的unionid和openid
        user.setUnionid(form.getUnionid());
        user.setOpenid(form.getOpenid());
        sysUserService.update(user);

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(user.getUserId());

        // 判断是否是初始密码
        boolean loginMdyPwd = false;
        if (user.getPassword().equals(new Sha256Hash("888888", user.getSalt()).toHex())) {
            loginMdyPwd = true;
        }

        // 给用户打微信标签
        setUserLabel(form.getOpenid());

        return RestResponse.success().put("token", token).put("expire", Constant.EXPIRE).put("userMsg",user).put("loginMdyPwd", loginMdyPwd);
    }


    @SysLog("登录2")
    @PostMapping("/sys/login2")
    public RestResponse login2(@RequestBody SysLoginForm form) {
        //用户信息
        List<SysUserEntity> list = new ArrayList<>();
        if (form.getLoginType() != null) {
            if (form.getLoginType() == 1) {
                String oaId = form.getOaId();
                if(oaId != null && !"".equals(oaId)) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("oaId", oaId);
                    list = sysUserService.queryAll(params);
                } else {
                    return RestResponse.error("您的OAID为空，请联系管理员，谢谢！");
                }
            } else if (form.getLoginType() == 2) {
                String dingId = form.getDingId();
                if(dingId != null && !"".equals(dingId)) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("dingId", dingId);
                    list = sysUserService.queryAll(params);
                } else {
                    return RestResponse.error("您的钉钉ID为空，请联系管理员，谢谢！");
                }
            }
        }
        SysUserEntity user = new SysUserEntity();
        if(list.size() > 0) {
            user = list.get(0);
        }else{
            return RestResponse.error("您无权限登录谢谢");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            return RestResponse.error("账号已被锁定,请联系管理员");
        }

        // 判断该账户是否已开通登录
        if (!(user.getCreateState() != null && user.getCreateState() == 1)) {
            return RestResponse.error("该账户还未开通登录权限，请联系管理员！");
        }

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(user.getUserId());

        // 判断是否是初始密码
        boolean loginMdyPwd = false;
        if (user.getPassword().equals(new Sha256Hash("888888", user.getSalt()).toHex())) {
            loginMdyPwd = true;
        }

        return RestResponse.success().put("token", token).put("expire", Constant.EXPIRE).put("userMsg",user).put("loginMdyPwd", loginMdyPwd);
    }


    /**
     * 退出系统
     *
     * @return RestResponse
     */
    @PostMapping("/sys/logout")
    public RestResponse logout(@RequestParam String token) {
        sysUserTokenService.logout(getUserId(),token);
        return RestResponse.success();
    }

    public void setUserLabel(String openId) throws IOException {
        Map map = new HashMap();
        List<String> openIds = new ArrayList<>();
        openIds.add(openId);
        map.put("tagId", 153);
        map.put("appId", "wxff67de16d9b463d5");
        map.put("openIds", openIds);
        String resultPost = HttpClient.sendPost(Vars.APPID_GETLABEL_URL, JsonHelper.toJsonString(map));
    }
}
