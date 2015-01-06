package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.been.RequestBeen;
import com.mayi.julyup.been.SystemMessageData;
import com.mayi.julyup.been.System_Message_Been;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.pictures.PublishedActivity;
import com.mayi.julyup.util.AlertDialogUtil;
import com.mayi.julyup.util.GsonUtil;
import com.mayi.julyup.util.ToastUtils;

@EActivity(R.layout.main)
public class MainActivity extends BaseActivity {

	@ViewById(R.id.head_image)
	ImageView head_image;
	@ViewById(R.id.upload_img_butt)
	Button upload_img_butt;
	@ViewById(R.id.upload_folder_butt)
	Button upload_folder_butt;
	@ViewById(R.id.download_file_butt)
	Button download_file_butt;
	@ViewById(R.id.question_butt)
	Button question_butt;
	@ViewById(R.id.about_butt)
	Button about_butt;
	@ViewById(R.id.phone_butt)
	Button phone_butt;
	@ViewById(R.id.exit_butt)
	ImageView exit_butt;
	@ViewById
	LinearLayout linear_right;
	@ViewById
	LinearLayout linear_left;
	@ViewById
	TextView text_content;
	@ViewById
	TextView text_title;
	public Dialog dialog=null;
	
	@AfterViews
	void init() {
		dialog=AlertDialogUtil.createLoadingDialog(this, "正在请求数据...");
		// 初始化上传任务
		UploadTaskInit();
	}

	private void UploadTaskInit() {
		if(!dialog.isShowing()){
			dialog.show();
		}
		
		JulyUp_RestClient.post_Session(getApplicationContext(),
				Urls.UploadTask_Init_URL, null, new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						Log.e(TAG, "初始化上传任务 :" + arg2);
						RequestBeen model = GsonUtil.getGson().fromJson(arg2,
								RequestBeen.class);

						if (!model.isSuccess()) {
							ToastUtils.show(context, model.getMessage());
						} else {
							ToastUtils.show(context, "初始化上传任务成功");
						}
					   if(dialog.isShowing()){
						   dialog.dismiss();
					   }
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
					   if(dialog.isShowing()){
						   dialog.dismiss();
					   }

					}
				});
		
		JulyUp_RestClient.post_Session(getApplicationContext(),
				Urls.Get_System_Message, null, new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						Log.e(TAG, "备注接口 :" + arg2);
						System_Message_Been model = GsonUtil.getGson().fromJson(arg2,
								System_Message_Been.class);
						if (model.isSuccess()) {
							SystemMessageData result = GsonUtil.getGson().fromJson(model.getResult(),
									SystemMessageData.class);
							text_content.setText(result.getMESSATE_CONTENT());
							text_title.setText(result.getMESSATE_TITLE());
						} else {
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {

					}
				});
		
	}

	@Click({ R.id.head_image, R.id.upload_img_butt, R.id.upload_folder_butt,
			R.id.download_file_butt, R.id.about_butt, R.id.question_butt,
			R.id.phone_butt, R.id.exit_butt ,R.id.linear_left, R.id.linear_right })
	void button_onclick(View v) {
		switch (v.getId()) {
		// 头像
		case R.id.head_image:
			break;
		// 上传图片
		case R.id.upload_img_butt:
			openActivity(PublishedActivity.class);
			break;
		// 上传历史
		case R.id.upload_folder_butt:
			// openActivity(UploadHistory_.class);
			openActivity(Upload_Unfinished_.class);
			break;
		case R.id.linear_left:
			openActivity(Upload_Unfinished_.class);
			break;
		// 下载文件
		case R.id.download_file_butt:
			openActivity(Download_.class);
			break;
		case R.id.linear_right:
			openActivity(Download_.class);
			break;
		// 企业中心
		case R.id.about_butt:
			openActivity(Company_.class);
			break;
		// FAQ
		case R.id.question_butt:
			openActivity(FAQ_.class);
			break;
		// 联系我们
		case R.id.phone_butt:
			openActivity(Connect_Me_.class);
			break;
		// 退出app
		case R.id.exit_butt:
			JulyUp_RestClient.post_Session(getApplicationContext(),
					Urls.LogOut_URL, null, LogoutHandler);
			isExit();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		 if(dialog.isShowing()){
			   dialog.dismiss();
		   }
		JulyUp_RestClient.post_Session(getApplicationContext(),
				Urls.LogOut_URL, null, LogoutHandler);
		isExit();
	};

	// 注销
	TextHttpResponseHandler LogoutHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int arg0, Header[] arg1, String result) {
			// TODO Auto-generated method stub
			System.out.println("result    :" + result);
			RequestBeen model = GsonUtil.getGson().fromJson(result,
					RequestBeen.class);
			if (!model.isSuccess()) {
				showShortToast(model.getMessage());
			}

		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable throwable) {
			// TODO Auto-generated method stub
			Log.e(TAG, throwable.getMessage());
		}
	};
}
