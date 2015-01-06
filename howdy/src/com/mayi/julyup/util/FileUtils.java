/**
 * 2014-10-28下午11:43:23
 */
package com.mayi.julyup.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.mayi.julyup.R;

/**
 * @ClassName: FileUtils
 * @package com.vatty.utils
 * @author mayi
 * @date 2014-10-28 下午11:43:23
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */

public class FileUtils {
	public static void readContentFromGet(String GET_URL,String session_value) throws IOException {
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
//        String getURL = GET_URL + "?username="
//                + URLEncoder.encode("fat man", "utf-8");
        URL getUrl = new URL(GET_URL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        connection.setRequestProperty("Cookie", session_value);
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到 服务器
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        System.out.println("=============================");
        System.out.println("Contents of get request");
        System.out.println("=============================");
        String lines;
        while ((lines = reader.readLine()) != null) {
            System.out.println(lines);
        }
        
        reader.close();
        // 断开连接
        connection.disconnect();
        System.out.println("=============================");
        System.out.println("Contents of get request ends");
        System.out.println("=============================");
    }
	
	
	
	// 判断目录是否有相应文件
	public static boolean Is_Have_Files(String Path, String filename,
			boolean IsIterative) // 搜索目录，文件名，是否进入子文件夹
	{

		File[] files = new File(Path).listFiles();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile()) {
				if (f.getName().equals(filename)) // 判断文件名是否相同
					return  true;
				if (!IsIterative)
					break;
			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
				Is_Have_Files(f.getPath(), filename, IsIterative);
		}
		return  false;
	}

	// 获取SD卡路径
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			sdDir = Environment.getDataDirectory();
		}
		return sdDir.toString();
	}

	// 打开文件
	public static void openFile(File currentPath, Context context) {
		List<ResolveInfo> list=new ArrayList<ResolveInfo>();
		Intent intent=null;
		try {
			if (currentPath != null && currentPath.isFile()) {
				String fileName = currentPath.getName();
				if (checkEndsWithInStringArray(fileName, context.getResources()
						.getStringArray(R.array.fileEndingImage))) {
					intent = OpenFiles.getImageFileIntent(currentPath);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingWebText))) {
					intent = OpenFiles.getHtmlFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingPackage))) {
					intent = OpenFiles.getApkFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingAudio))) {
					intent = OpenFiles.getAudioFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingVideo))) {
					intent = OpenFiles.getVideoFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingText))) {
					intent = OpenFiles.getTextFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingPdf))) {
					intent = OpenFiles.getPdfFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingWord))) {
					intent = OpenFiles.getWordFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingExcel))) {
					intent = OpenFiles.getExcelFileIntent(currentPath);
//					context.startActivity(intent);
				} else if (checkEndsWithInStringArray(fileName, context
						.getResources().getStringArray(R.array.fileEndingPPT))) {
					intent = OpenFiles.getPPTFileIntent(currentPath);
//					context.startActivity(intent);
				} else {
					Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT)
							.show();
				}
				
				if(intent!=null){
					 list=context.getPackageManager().queryIntentActivities(intent, 0);
						if(list!=null&list.size()>0){
							for(int i=0;i<list.size();i++){
								ResolveInfo ri=list.get(0);
								String package_name=ri.activityInfo.packageName;
								System.out.println("package_name:"+package_name);
								if(list.size()==1&&"com.tencent.mobileqq".equals(package_name)){
									Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
									return;
								}
							}
							context.startActivity(intent);
						}else{
							Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT)
							.show();
						}
				}else{
					Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT)
					.show();
				}
				
			} else {
				Toast.makeText(context, "对不起，这不是文件！", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(context, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT)
			.show();
		}
		
	}

	// 检查后缀文件名
	private static boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
}
