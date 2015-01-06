package com.mayi.julyup.application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.mayi.julyup.service.NetStateService;
import com.mayi.julyup.util.LogUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @author mayi 2014-7-10 下午4:44:33 document:
 */

public class BeeApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();

	/**
	 * 初始化ImageLoader
	 */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"HowDy/Cache");// 获取到缓存的目录地址
		Log.e("cacheDir", cacheDir.getPath());
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.memoryCacheExtraOptions(480, 800)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)设置缓存的详细信息，最好不要设置这个
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG,
				// 75,null)
				// 线程池内加载的数量1-5
				.threadPoolSize(3)
				// 线程优先级
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				// You can pass your own memory cache
				.memoryCache(new WeakMemoryCache())
				// implementation你可以通过自己的内存缓存实现
				// .memoryCacheSize(10 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				// 将保存的时候的URI名称用MD5
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())

				// 将保存的时候的URI名称用HASHCODE加密
				// .discCacheFileNameGenerator(new HashCodeFileNameGenerator())

				// .tasksProcessingOrder(QueueProcessingType.LIFO)
				// 缓存的File数量
				// .discCacheFileCount(100)
				// 自定义缓存路径
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// connectTimeout (5 s), readTimeout (30 s)超时时间
				// .imageDownloader(new BaseImageDownloader(context, 5 * 1000,30
				// * 1000))
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
		StartService();
	}

	/**
	 * 开始服务
	 */
	public void StartService() {
		LogUtil.e("BeeApplication", "StartService......");
		// 启动网络监听service
		Intent intent = new Intent(getApplicationContext(),
				NetStateService.class);
		startService(intent);
	}

	/**
	 * 停止服务
	 */
	public void StopService() {
		LogUtil.i("BeeApplication", "StopService......");
		// 停止网络监听service
		Intent intent = new Intent(getApplicationContext(),
				NetStateService.class);
		stopService(intent);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		// 整体摧毁的时候调用这个方法
	}

	// 添加activity到list
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 退出app
	public void exit() {
		LogUtil.i("BeeApplication", "exit......");
		StopService();
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	// 友盟集成服务测试
	public String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
