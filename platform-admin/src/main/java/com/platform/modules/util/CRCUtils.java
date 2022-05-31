package com.platform.modules.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @program byh-service-referral
 * @description:
 * @author: SUNSHANSHAN
 * @create: 2022/02/14 10:07
 */
public class CRCUtils {

    public static int FindCRC(byte[] data){
        int CRC=0;
        int genPoly = 0Xaa;
        for(int i=0;i<data.length; i++){
            CRC ^= data[i];
            for(int j=0;j<8;j++){
                if((CRC & 0x80) != 0){
                    CRC = (CRC << 1) ^ genPoly;
                }else{
                    CRC <<= 1;
                }
            }
        }
        CRC &= 0xff;//保证CRC余码输出为2字节。
        return CRC;
    }
    public static void main(String[] args) {
        String abc = "12431312sdrfd";
        int crc2 = FindCRC(abc.getBytes());
        String crc16 = Integer.toHexString(crc2);//把10进制的结果转化为16进制
        //如果想要保证校验码必须为2位，可以先判断结果是否比16小，如果比16小，可以在16进制的结果前面加0
        if(crc2 < 16 ){
            crc16 = "0"+crc16;
        }
        System.out.println(crc16);
    }


}
