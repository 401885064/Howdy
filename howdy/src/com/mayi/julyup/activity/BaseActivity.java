package com.mayi.julyup.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mayi.julyup.R;
import com.mayi.julyup.application.BeeApplication;
import com.mayi.julyup.util.LogUtil;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
	public static String JSESSIONID="";
	public static final String TAG = "BaseActivity";
	protected Context context = null;
	protected AlertDialog mAlertDialog;
	protected AsyncTask<?, ?, ?> mRunningTask;
	private int network_err_count = 0;
	private BeeApplication beeapplication;
	public static SharedPreferences perference;
//	PowerManager.WakeLock mWakeLock = null; 
	/**
	 * ***************************** 【Activity LifeCycle For Debug】******************************************
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		LogUtil.d(TAG, this.getClass().getSimpleName()+ " onCreate() invoked!!");
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//		mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "My Tag");
		context = this;
		beeapplication = (BeeApplication) getApplication();
		beeapplication.addActivity(this);
		perference=context.getSharedPreferences("howdy", MODE_PRIVATE);
		
		//Umeng集成服务测试    获取测试机的信息
//		String DevStr=beeapplication.getDeviceInfo(getApplicationContext());
//		Log.e("DevStr  :", DevStr+"");
	}

	@Override
	protected void onStart() {
//		LogUtil.d(TAG, this.getClass().getSimpleName() + " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
//		LogUtil.d(TAG, this.getClass().getSimpleName()
//				+ " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
//		LogUtil.d(TAG, this.getClass().getSimpleName()
//				+ " onResume() invoked!!");
		MobclickAgent.onResume(this);
		super.onResume();
		
//		mWakeLock.acquire(); 
	}

	@Override
	protected void onPause() {
//		LogUtil.d(TAG, this.getClass().getSimpleName() + " onPause() invoked!!");
		MobclickAgent.onPause(this);
		super.onPause();
//		mWakeLock.release();
	}

	@Override
	protected void onStop() {
//		LogUtil.d(TAG, this.getClass().getSimpleName() + " onStop() invoked!!");
		super.onStop();
	}

	@Override
	public void onDestroy() {
//		LogUtil.d(TAG, this.getClass().getSimpleName()
//				+ " onDestroy() invoked!!");
		super.onDestroy();

		if (mRunningTask != null && mRunningTask.isCancelled() == false) {
			mRunningTask.cancel(false);
			mRunningTask = null;
		}
//		if (mAlertDialog != null) {
//			mAlertDialog.dismiss();
//			mAlertDialog = null;
//		}
	}

/*****************************************************perference**************************************************************/
	protected boolean perference_putStringValue(String key,String value){
	   return perference.edit().putString(key, value).commit();
	}
	protected boolean perference_putStringValue(String key,int value){
	    return perference.edit().putInt(key, value).commit();
	}
	protected boolean perference_putStringValue(String key,float value){
	    return perference.edit().putFloat(key, value).commit();
	}
	protected boolean perference_putStringValue(String key,boolean value){
	    return perference.edit().putBoolean(key, value).commit();
	}
	
	protected String perference_getStringValue(String key){
	    return perference.getString(key, "");
	}
	protected int perference_getIntValue(String key){
	    return perference.getInt(key, 0);
	}
	protected float perference_getFloatValue(String key){
	    return perference.getFloat(key, 0L);
	}
	protected boolean perference_getBooleanValue(String key){
	    return perference.getBoolean(key, false);
	}
	
/*****************************************************Toast**************************************************************/	
	
	protected void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	protected void showLongToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
	}

	protected void showShortToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
	}

	protected boolean hasExtra(String pExtraKey) {
		if (getIntent() != null) {
			return getIntent().hasExtra(pExtraKey);
		}
		return false;
	}

	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
		/**
		 * 切换activity动画
		 * 在startActivity（）或finish（）后，调用overridePendingTransition（R.
		 * anim.**in,R.anim.**out）方法.
		 */
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	protected void openActivityForResult(Class<?> pClass, Bundle pBundle,
			int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}


	/**
	 * 隐藏键盘
	 * 
	 * @param view
	 */

	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 内存空间不够
	 */
	protected void handleOutmemoryError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, "内存空间不足！", Toast.LENGTH_SHORT).show();
				// finish();
			}
		});
	}

	
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void defaultFinish() {
		super.finish();
	}

	/**
	 * @return
	 */
	public String getSD_Path() {
		// 获取sd卡路径
		String SDPath = Environment.getExternalStorageDirectory().getPath();

		String sdStatus = Environment.getExternalStorageState();
		// 检测sd是否可用
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			showShortToast("SD card is not avaiable/writeable right now.");
			SDPath = "";
		}
		return SDPath;
	}

	/**
	 * 返回 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	 * 
	 * @return
	 */
	public String getExternalFilesDirPath() {
		return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
				.getAbsolutePath();
	}

	/**
	 * 返回 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	 * 
	 * @return
	 */
	public String getExStringCacheDir() {
		return context.getExternalCacheDir().getAbsolutePath();
	}

	public void isExit() {
		new AlertDialog.Builder(context).setTitle("确定退出吗?")
				.setNeutralButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						beeapplication.exit();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}
}
