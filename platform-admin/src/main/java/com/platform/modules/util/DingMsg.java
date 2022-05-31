package com.platform.modules.util;

import cn.emay.util.JsonHelper;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationSendbytemplateRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationSendbytemplateResponse;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageType;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import com.platform.modules.qkjInterface.entity.UserMsgEntity;
import com.taobao.api.ApiException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DingMsg {
    @Autowired
    private AccesstokenService accesstokenService;
    public static void main(String[] args)  throws Exception{
        DingMsg d=new DingMsg();
        //d.sendLinkMessage("scrm","1","01672211281464","01672211281464","7b2338f0a5233db0b1a0dfe95763e3a9");
        //d.sendLinkMessageResult("cccsss11","22333","01672211281464","","4dd3d3a8c8dc3df48dbcc22d68e0c3b7");
        UserMsgEntity userMsgEntity = new UserMsgEntity();
        userMsgEntity.setDinglist("01672211281464");
        userMsgEntity.setTitle("孙珊珊的测试3311");
        userMsgEntity.setMsg("孙珊珊的测试3311");
        userMsgEntity.setMobilelist("18810242427");
        userMsgEntity.setUrl("http://admin.mall.qkj.com.cn/mob/Account/orderinfo?orderid=1591");
       String queryJsonStr = JsonHelper.toJsonString(userMsgEntity, "yyyy-MM-dd HH:mm:ss");
//        Map<String,Object> paramsMap = new HashMap<String,Object>();
//        paramsMap.put("dinglist","01672211281464");
//        paramsMap.put("title","000");
//        paramsMap.put("msg","333");
//        paramsMap.put("mobilelist","18810242427");
//        HttpUtil.post("https://127.0.0.1:8888/platform-plus/qkjvip/sendmsgbatch/sendmsg",paramsMap);
        String resultPost = HttpClient.sendPost("http://127.0.0.1:8888/platform-plus/qkjvip/sendmsgbatch/sendmsg",queryJsonStr);
        System.out.println(resultPost);

    }
    public void sendLinkMessage(String title,String msg,String user,String singuuid,String accessToken) throws Exception{
        //发送消息
        MessageBody.LinkBody linkBody = new MessageBody.LinkBody();
        linkBody.setPicUrl("@lALOACZwe2Rk");
        linkBody.setTitle(title);
        linkBody.setText(msg);
        // 发送微应用消息
        String toUsers = user;
        String toParties = Vars.TO_PARTY;
        String agentId = Vars.AGENT_ID;
        LightAppMessageDelivery lightAppMessageDelivery = new LightAppMessageDelivery(toUsers, toParties, agentId);
        lightAppMessageDelivery.withMessage(MessageType.LINK, linkBody);
        MessageHelper.Receipt resultPost = MessageHelper.send(accessToken, lightAppMessageDelivery);
    }

    public String  sendLinkMessageResult(String title,String msg2,String user,String singuuid,String accessToken,String url){
        //发送消息
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.valueOf(Vars.AGENT_ID));
        request.setUseridList(user);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(title);
        msg.getLink().setText(msg2);
        msg.getLink().setMessageUrl(url);
        msg.getLink().setPicUrl(url);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        String flag = "0";
        try {
            rsp = client.execute(request, accessToken);
        } catch (ApiException e) {
            //e.printStackTrace();
            flag = "调用接口失败";
        }
        String result = rsp.getBody();
        JSONObject resultObject = JSON.parseObject(result);
        if ("0".equals(resultObject.get("errcode").toString())) {  //调用成功
            flag = "0";
        } else {
            flag = result;
        }
       return  flag;
    }

}
