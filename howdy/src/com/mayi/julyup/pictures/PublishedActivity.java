package com.mayi.julyup.pictures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.Header;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.activity.BaseActivity;
import com.mayi.julyup.activity.Upload_Unfinished_;
import com.mayi.julyup.adapter.Published_GridAdapter;
import com.mayi.julyup.configs.Contents;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.pop.Picture_PopupWindows;
import com.mayi.julyup.util.ToastUtils;

/**
 * 上传图片activity
 * 
 * @author sondon
 * 
 */
public class PublishedActivity extends BaseActivity implements
		View.OnClickListener {
	public final static int Adapter_Changed = 0x111;
	public final static int Uplod_Finish = 0x222;
	public final static int Close_Activity=0x333;
	private GridView noScrollgridview;
	private Published_GridAdapter adapter;
	private TextView activity_selectimg_send, activity_selectimg_cancel;
	private static int success, fail;
	public ArrayList<Integer> RemovePosition=new ArrayList<Integer>();

	public Handler myhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Uplod_Finish:
				int count = success + fail;
				if(count==Bimp.bmp.size()){
					adapter.IsUpLoading=false;
					activity_selectimg_send.setTextColor(getResources().getColor(R.color.white));
					activity_selectimg_send.setEnabled(true);
					 //toast
					if (count == Bimp.drr.size() && fail == 0) {
						ToastUtils.show(context, "全部上传成功");
						ClearAllImgs();
					} else if (count == Bimp.drr.size() && fail > 0) {
						//对RemovePosition进行排序
						 Collections.sort(RemovePosition, new My_Comparator()); 
						 Log.e(TAG, "RemovePosition :"+Arrays.toString(RemovePosition.toArray()));
						//移除上传成功后的数据
						for(int i=0;i<RemovePosition.size();i++){
							Log.e(TAG, "移除第"+RemovePosition.get(i)+"个");
							Bimp.bmp.remove((int)RemovePosition.get(i));
							Bimp.drr.remove((int)RemovePosition.get(i));
						}
						ToastUtils.show(context,fail+ "张上传失败");
					}
					//刷新listview
					adapter.notifyDataSetChanged();
				}
				break;
			case Adapter_Changed: 
				adapter.notifyDataSetChanged();
				if(adapter.IsUpLoading){
					sendEmptyMessageDelayed(Adapter_Changed,500);
				}
				break;
			case Close_Activity:
				Log.e("mayi", "finish ...");
				PublishedActivity.this.finish();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
		Init();
	}

	@Override
	public void onBackPressed() {
		if(adapter.IsUpLoading){
			ToastUtils.show(context, "正在上传图片，请稍后！");
			return;
		}
		ClearAllImgs();
		openActivity(Upload_Unfinished_.class);
		finish();
		super.onBackPressed();
	}

	// 初始化
	public void Init() {
		Log.e(TAG, "sdk   :"+VERSION.SDK_INT);
		//通知扫描相机相册
		if(Integer.valueOf(VERSION.SDK_INT)<19){
			this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		}else{
			MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/"}, null, null);
		}
		
		// 加载图片
		loading();
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		// 设置点击选择时的黄色背景框的颜色
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new Published_GridAdapter(this);
		
		noScrollgridview.setAdapter(adapter);
		// 适配器点击事件
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				if (position == Bimp.bmp.size()) {
					// 点击了"+"号，选择图片来源：相册或者照相机
					new Picture_PopupWindows(PublishedActivity.this,noScrollgridview);
				} else {
					// 点击了grild列表中的图片，进行图片浏览
					Intent intent = new Intent(PublishedActivity.this,PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
					PublishedActivity.this.finish();
				}
			}
		});

		// 上传点击事件
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(this);

		// 取消点击事件
		activity_selectimg_cancel = (TextView) findViewById(R.id.activity_selectimg_cancel);
		activity_selectimg_cancel.setOnClickListener(this);
	}

	// 加载图片
	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					System.out.println("loading ....");
					if (Bimp.max == Bimp.drr.size()) {
						Message message = new Message();
						message.what = PublishedActivity.Adapter_Changed;
						myhandler.sendMessage(message);
						break;
					} else {
						try {
							String path = Bimp.drr.get(Bimp.max);
							System.out.println(path);
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							FileUtils.saveBitmap(bm, "" + newStr);
							Bimp.max += 1;
							Message message = new Message();
							message.what = PublishedActivity.Adapter_Changed;
							myhandler.sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	@Override
	protected void onRestart() {
		loading();
		super.onRestart();
	}

	// ///////////////////////////////////////////////////获取图片///////////////////////////////////////////////////////
	//从相册获取
	public void photo(){
		Intent intent = new Intent(PublishedActivity.this, PictureActivity.class);
		startActivity(intent);
		myhandler.sendEmptyMessageDelayed(PublishedActivity.Close_Activity,500);
	}
	
	// 从相机获取
	private static final int TAKE_PICTURE = 0x000000;
	private static String path = "";

	public void camera() {
		File file = null;
		// 目录
		String dir = FileUtils.SDPATH;
		// 文件
		String file_path = dir + String.valueOf(System.currentTimeMillis())
				+ ".jpg";

		// 判断目录是否存在
		File file_dir = new File(dir);
		if (!file_dir.exists()) {
			System.out.println("image filedir is :" + file_dir.mkdirs());
		}
		// 判断文件是否存在
		file = new File(file_path);

		if (!file.exists()) {
			try {
				System.out.println("image file is :" + file.createNewFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 获取文件路径
		path = file.getPath();
		// 解析为Uri
		Uri imageUri = Uri.fromFile(file);
		// 设置Intent
		Intent OpenPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		OpenPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(OpenPhotoIntent, TAKE_PICTURE);
	}

	/**
	 * ActivityResult回调
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultCode  :" + resultCode);
		switch (requestCode) {
		case TAKE_PICTURE:
			// RESULT_OK ：Standard activity result: operation succeeded.
			if (Bimp.drr.size() < 9 && resultCode == RESULT_OK) {
				Bimp.drr.add(path);
			}
			break;
		}
	}

	// 监听按钮事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 上传按钮
		case R.id.activity_selectimg_send:
			if(Bimp.drr.size()==0){
				ToastUtils.show(context, "没有选择图片！");
				return;
			}else{
				activity_selectimg_send.setTextColor(getResources().getColor(R.color.black));
				activity_selectimg_send.setEnabled(false);
			}
			
			success = 0;
			fail = 0;
			adapter.IsUpLoading=true;
			//通知adapter更新
			myhandler.sendEmptyMessageDelayed(Adapter_Changed, 1000);
			for (int position = 0; position < Bimp.drr.size();position++) {
				//设置上传参数
				RequestParams params = new RequestParams();
				params.put("TASK_ID ", Contents.upload_task_id);
				File myFile = new File(Bimp.drr.get(position));
				//判断文件是否存在
				if (!myFile.exists()) {
					ToastUtils.show(context, "文件已经不存在！");
					return;
				}
				try {
					params.put("profile_picture", myFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//调用上传方法
				JulyUp_RestClient.post_Session(getApplicationContext(),
						Urls.UploadImage_URL, params,
						new MyResponseHandler(position));
			}
			break;
		// 返回键
		case R.id.activity_selectimg_cancel:
			if(adapter.IsUpLoading){
				ToastUtils.show(context, "正在上传图片，请稍后！");
				return;
			}
			ClearAllImgs();
			openActivity(Upload_Unfinished_.class);
			finish();
			break;
		default:
			break;
		}
	}

	// 清空所有图片文件
	public void ClearAllImgs() {
		Bimp.drr.clear();
		Bimp.max = 0;
		Bimp.bmp.clear();
		FileUtils.deleteDir();
	}
	
	//上传请求响应
	class MyResponseHandler extends TextHttpResponseHandler{
		int position;
		public MyResponseHandler(int position){
			this.position=position;
		}
			
		@Override
		public void onProgress(int bytesWritten,
				int totalSize) {
			super.onProgress(bytesWritten, totalSize);
			adapter.getPbMap().put("max"+position, totalSize);
			adapter.getPbMap().put("current"+position, bytesWritten);
			//这里做notifyDataSetChanged的话，负载太重
//			adapter.notifyDataSetChanged();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1,
				String arg2) {
			if (arg0 == 200) {
				// ToastUtils.show(context, "上传成功");
				success++;
				adapter.getPbMap().put("complete"+position, 88);
				RemovePosition.add(position);
				myhandler.sendEmptyMessage(PublishedActivity.Uplod_Finish);
				}
			}
			
		@Override
		public void onFailure(int arg0, Header[] arg1,
				String arg2, Throwable arg3) {
			// TODO Auto-generated method stub
			fail++;
			myhandler.sendEmptyMessage(PublishedActivity.Uplod_Finish);
			// ToastUtils.show(context, "上传失败！");
		}
	}
	
	//自定义排序算法
	class My_Comparator implements Comparator<Integer>{
		@Override
		public int compare(Integer lhs, Integer rhs) {
			
			return rhs-lhs;
		}
		
	}
}
