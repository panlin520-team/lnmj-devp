package com.lnmj.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @Auther: panlin
 * @Date: 2019/5/10 11:19
 * @Description:
 */
public class FileUtil {
    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return genImageName() + getSuffix(fileOriginName);
    }
    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }
    /**
     * 生成随机图片名
     */
    public static String genImageName() {        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }

}
