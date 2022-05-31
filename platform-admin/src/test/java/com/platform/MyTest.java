/*
 * 项目名称:platform-plus
 * 类名称:MyTest.java
 * 包名称:com.platform
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/17 17:40            liuqianru    初版做成
 *
 */
package com.platform;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.platform.common.utils.JwtUtils;
import com.platform.modules.util.HttpClient;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyTest
 *
 * @author liuqianru
 * @date 2020/8/17 17:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Autowired
    private JwtUtils jwtUtils;
    private WxMpService wxService;

    @Test
    public void main() throws WxErrorException, IOException {
        /*获取access_token start*/
//        String appid = "wxc64e4c7b44af0595";
//        String appsecret = "3fd8281d244ecdc38b77bd4ad7cdea28";
//        List<NameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("grant_type","client_credential"));
//        list.add(new BasicNameValuePair("appid","wxc64e4c7b44af0595"));
//        list.add(new BasicNameValuePair("secret","3fd8281d244ecdc38b77bd4ad7cdea28"));
//        String url = "https://api.weixin.qq.com/cgi-bin/token";
//        String strResult = HttpClient.doHttpGet(url, list);
        /*获取access_token end*/

        /*获取粉丝列表 start*/
//        String url = "https://api.weixin.qq.com/cgi-bin/user/get";
//        List<NameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("access_token","36_enDka3cZ_eXqCovbs8wL-bdaya1VWMpJs9hrqUMrDOcq3DTZlat3UTALNrQidUaxTdsuNUWsMQ_9262Rr8f3Ie1hG3MU4XAX7sXdMmuPE7Ie2Y-voxo9EEHlLuelqL4wyVUG4VeOFphzsvY9OYOjACAEOE"));
//        String strResult = HttpClient.doGet(url, list);
//        System.out.print(strResult);
        /*获取粉丝列表 end*/

        // 创建工作簿对象
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建工作表对象
        XSSFSheet sheet = wb.createSheet("我的工作表");
        // 创建绘图对象
        XSSFDrawing p = sheet.createDrawingPatriarch();
        // 创建单元格对象,批注插入到4行,1列,B5单元格
        //XSSFCell cell = sheet.createRow(4).createCell(1);
        // 插入单元格内容
        //cell.setCellValue(new XSSFRichTextString("批注"));
        final String[] excelHeaderArr = {"身份证号", "姓名", "性别", "专业", "金额", "支付时间"};
        // 生成表格第一行 第一行表头
        final XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;
        for (int i = 0; i < excelHeaderArr.length; i ++ ) {
            cell = row.createCell(i);
            // 设置数值
            cell.setCellValue(excelHeaderArr[i]);
            // 设置样式
//            cell.setCellStyle(style);
            // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0,0, (short) 3, 3, (short) 5, 6));
            // 输入批注信息
            comment.setString(new XSSFRichTextString("这是批注内容!\n\r这是批注内容!\n\r这是批注内容!\n\r这是批注内容!\n\r这是批注内容!"));
            // 添加作者,选中B5单元格,看状态栏
            comment.setAuthor("toad");
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        }
        // 获取批注对象
        // (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
        // col2, int row2)
        // 创建输出流
        FileOutputStream out = new FileOutputStream("d:/writerPostil.xlsx");

        wb.write(out);
        // 关闭流对象
        out.close();
    }

    @Test
    public void one() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "doc/exportTemp_image.xls", true);
        Map<String, Object> map = new HashMap<String, Object>();
        // sheet 2
        map.put("month", 10);
        Map<String, Object> temp;
        for (int i = 1; i < 8; i++) {
            temp = new HashMap<String, Object>();
            temp.put("per", i * 10);
            temp.put("mon", i * 1000);
            temp.put("summon", i * 10000);
            ImageEntity image = new ImageEntity();
            image.setHeight(200);
            image.setWidth(500);
            image.setUrl("imgs/company/baidu.png");
            temp.put("image", image);
            map.put("i" + i, temp);
        }
        Workbook book = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/exportTemp_image.xls");
        book.write(fos);
        fos.close();

    }
}
