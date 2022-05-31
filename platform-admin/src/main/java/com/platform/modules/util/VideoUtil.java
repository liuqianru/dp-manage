/*
 * 项目名称:platform-plus
 * 类名称:VideoUtil.java
 * 包名称:com.platform
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/6/24 16:58            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import com.platform.modules.oss.cloud.UploadFactory;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * VideoUtil
 *
 * @author liuqianru
 * @date 2021/6/24 16:58
 */
public class VideoUtil {

    /**
     * base64字符串转换成图片
     * @param imgStr      base64字符串
     */
    public static String base64ToImage(String imgStr) {
        String url = "";
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(imgStr);
            url = UploadFactory.build().uploadSuffix(buffer, ".jpg");
//            FileOutputStream out = new FileOutputStream(imgFilePath);
//            out.write(buffer);
//            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 将截取的帧生成文件或者生成base64
     * @param frame
     * @return
     */
    private static String getBase64FromFrame(Frame frame) {
        String imgFormat = "jpg";
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage srcBi = converter.getBufferedImage(frame);
        // 可以选择对截取的帧进行等比例缩放
        int owidth = srcBi.getWidth();
        int oheight = srcBi.getHeight();
        int width = 420;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            // ImageIO工具类提供了将图片写成文件或者outputStream中
//            ImageIO.write(bi, imgFormat, targetFile);
            ImageIO.write(bi, imgFormat, output);
            // 这里需要获取图片的base64数据串，所以将图片写到流里面
            return new BASE64Encoder().encode(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param fileUrl  源视频文件Url路径
     * @param stepSecond 每隔几秒取一帧，默认1200s
     * @param count 最多获取几帧
     * @return 返回截取的帧的Base64串列表
     * @throws Exception
     */
    public static List<String> fetchPicFromVideo(String fileUrl, Integer stepSecond, Integer count) throws Exception {
        List<String> picBase64List = new ArrayList<>();
        stepSecond = stepSecond == null ? 1200 : stepSecond;

        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(fileUrl);
        ff.start();
        long timeLength = ff.getLengthInTime();
        Frame frame = ff.grabImage();
        long startTime = frame.timestamp;
        long timestamp = 0;
        int second = 0;
        int picNum = 0;
        while (timestamp <= timeLength) {
            timestamp = startTime + second * 1000000L;
            ff.setTimestamp(timestamp);
            frame = ff.grabImage();
            if (frame != null) {
                if (frame.image != null) {
                    picBase64List.add(getBase64FromFrame(frame));
                    picNum++;
                    if (count != null && picNum == count) {
                        break;
                    }
                }
            }
            second += stepSecond;
        }
        ff.stop();
        return picBase64List;
    }


    public static void main(String[] args) {
        try {
            String outputPath = "E:/";
            String videoPath = "http://images.qkjebiz.qkjchina.com//qkjebiz/20210625/60ea2f5944894c588d1a1c09053b4ab2.mp4";
            List<String> files = VideoUtil.fetchPicFromVideo(videoPath, 600, 8);
            // 为了验证base64串的正确性，解码生成图片文件
            for (String imgBase64 : files) {
                String fileName = String.valueOf(System.currentTimeMillis());
                String targetFile = outputPath + File.separator + fileName + ".jpg";
                base64ToImage(imgBase64);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
