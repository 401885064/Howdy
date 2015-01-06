package com.mayi.julyup.configs;

import com.mayi.julyup.util.FileUtils;



public class Configs {
    // 分钟
    public static int Content_ListCacheTime = 5;
    public static int Content_ContentCacheTime = 60 * 24 * 3;
    public static int ImageCacheTime = 60 * 24 * 15;
    public static int Content_DefaultCacheTime = 60 * 24 * 3;

    public static int DiscussCacheTime = 60;

    public static boolean IsRealName = false;
    //图片后缀
    public static String IMAGE_SUFFIX = ".jpg";
    public static String IMAGE_TEMP = "image_temp";
    //拍照文件名
    public static String fname = "";
    //编码格式
    public static final String EnCode="UTF-8";
    public static final String Download_Dir=FileUtils.getSDPath()+"/Download/";
    
}
