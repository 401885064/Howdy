package com.sondon.util;

import android.graphics.Bitmap;

import com.mayi.julyup.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class Options {
	private static DisplayImageOptions options=null;
    /**
     * 新闻列表中用到的图片加载配置
     */
    public static DisplayImageOptions getListOptions() {
    	if(options !=null){
    		 return options;
    	}
    	
        options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.image_loading)
                        // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.load_error)
                        // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.load_error)
                        // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                        // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                        //保留Exif信息
                .considerExifParams(false)
                        // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                 // .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
                  // 设置图片下载前的延迟
//                .delayBeforeLoading(100)//int 
//                delayInMillis为你设置的延迟时间
                 // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//              .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        return options;
    }
}
