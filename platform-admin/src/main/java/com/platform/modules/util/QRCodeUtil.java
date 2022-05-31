package com.platform.modules.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.platform.modules.oss.cloud.UploadFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * <p>QRCodeUtil</p>
 *
 * @author lvhaosir6
 * @version 1.0.0
 * @date 2020/7/8
 */
@Slf4j
@UtilityClass
public class QRCodeUtil {
    /**
     * 生成包含字符串信息的二维码图片
     * @param outputStream 文件输出流路径
     * @param content 二维码携带信息
     * @param qrCodeSize 二维码图片大小
     * @param imageFormat 二维码的格式
     * @throws WriterException
     * @throws IOException
     */
    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws WriterException, IOException{
        //设置二维码纠错级别ＭＡＰ
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int revise = matrixWidth * 2 / 9;
        BufferedImage image = new BufferedImage(matrixWidth-revise, matrixWidth-revise, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-revise/2, j-revise/2, 1, 1);
                }
            }
        }
        return ImageIO.write(image, imageFormat, outputStream);
    }


    public String createQrCode(String content, int qrCodeSize, String imageFormat) throws WriterException, IOException{

        //设置二维码纠错级别ＭＡＰ
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int revise = matrixWidth * 2 / 9;
        BufferedImage image = new BufferedImage(matrixWidth-revise, matrixWidth-revise, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-revise/2, j-revise/2, 1, 1);
                }
            }
        }


        ImageIO.write(image, "jpg", out);
        byte[] tagInfo=out.toByteArray();
        String url = UploadFactory.build().uploadSuffix(tagInfo, imageFormat);
        return  url;
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static String readQrCode(InputStream inputStream) throws IOException{
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null ;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }
        return result.getText();
    }

    public String imgUrl(){
        try{
            String redirect_uri = URLEncoder.encode("http://www.baidu.com", "GBK");
            String wxurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx24b8ba9745158d1d&redirect_uri="+redirect_uri+"&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
            String url=createQrCode(wxurl,90,"JPEG");
            System.out.println(url);
        }catch (IOException e){

        }catch (WriterException e1){

        }

        return null;
    }

    /**
     * 测试代码
     * @throws WriterException
     */
    public static void main(String[] args) throws IOException, WriterException {
        imgUrl();

//        String content = "http://baidu.com.cn";
//        String path = "D:\\test.jpg";
//        createQrCode(new FileOutputStream(new File(path)), content,90,"JPEG");
//        String info = readQrCode(new FileInputStream(new File(path)));
//        System.out.println(info);
    }

}