/*
 * 项目名称:platform-plus
 * 类名称:MyTest2.java
 * 包名称:com.platform
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/9/23 14:03            liuqianru    初版做成
 *
 */
package com.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MyTest2
 *
 * @author liuqianru
 * @date 2020/9/23 14:03
 */
public class MyTest3 {
    @Test
    public static void main(String[] args) throws UnknownHostException {
        String[] appidAttr = {};
        List<String> appidList = Arrays.asList(appidAttr);
        List<String> aaa = new ArrayList<String>(appidList);
        aaa.remove("012345678987654321");
        System.out.print(aaa);
        System.out.println(System.getProperty("os.name"));

        InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
        String hostAddress = address.getHostAddress();//192.168.0.121
        System.out.println(address.getHostName());//主机名
        InetAddress address1 = InetAddress.getByName("www.wodexiangce.cn");//获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地
        String hostAddress1 = address1.getHostAddress();//124.237.121.122
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");//根据主机名返回其可能的所有InetAddress对象
        for (InetAddress addr : addresses) {
            System.out.println(addr);
        }
    }
}
