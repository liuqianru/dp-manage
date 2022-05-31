/*
 * 项目名称:platform-plus
 * 类名称:HttpClient.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/6 10:14            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * HttpClient 使用httpclient调用外部接口
 *
 * @author liuqianru
 * @date 2020/8/6 10:14
 */
public class HttpClient {
    /**
     * get 请求
     *
     * @return
     */
    public static String doGet(String url, List<NameValuePair> params) {
        String result = null;
        //1.获取httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //接口返回结果
        CloseableHttpResponse response = null;
        String paramStr = null;
        try {
            paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("?");
            sb.append(paramStr);
            //2.创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            //3.设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            /*此处可以添加一些请求头信息，例如：
            httpGet.addHeader("content-type","text/xml");*/
            //4.提交参数
            response = httpClient.execute(httpGet);
            //5.得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            //6.判断响应信息是否正确
            if (HttpStatus.SC_OK != statusCode) {
                //终止并抛出异常
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            //7.转换成实体类
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //8.关闭所有资源连接
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * http post 请求
     */
    public static String doPost(String strUrl) throws IOException {
        URL url = new URL(strUrl);
        URI uri = null;
        try {
            uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpPost httpRequest = new HttpPost(uri);
        HttpResponse response = new DefaultHttpClient().execute(httpRequest);
        if (response.getStatusLine().getStatusCode() == 200) {// 获取调用api返回数据
            // doing something
            InputStream content = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(content, "UTF-8");
            BufferedReader bufr = new BufferedReader(reader);// 缓冲
            String str;
            while ((str = bufr.readLine()) != null) {
                return str;
            }
        }
        return null;
    }

    public static String sendGet(String path) {
        String result = "";
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
//            conn.setReadTimeout(5000);
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();

            /**GET方法请求*****end*/

            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
                result += str;
                System.out.println(result);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            System.out.println("第三方api的get请求调用结束");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url, String param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();// 设置通用请求的属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");// 我加的一个头
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 我加的一个头
            conn.setRequestProperty("Dms-Access-Token", "XXXXXXXXXXXXXXXXXXXXXXX");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    result += line;

                }
            } catch (IOException e) {
                System.out.println("发送POST请求出现异常");
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return result;
    }
}
