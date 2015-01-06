package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.adapter.Download_List_Adapter;
import com.mayi.julyup.been.FilesBeen;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.util.AlertDialogUtil;
import com.mayi.julyup.util.GsonUtil;

@EActivity(R.layout.my_download_list)
public class MyDownloadListActivity extends BaseActivity {
	@ViewById
	ListView download_list;
	@ViewById
	TextView my_download_title;
	@ViewById
	TextView donwnload_back;
	@Bean
	Download_List_Adapter download_list_adapter;
	Dialog dialog;
	@AfterViews
	void init(){
		dialog = AlertDialogUtil.createLoadingDialog(this, "正在请求数据...");
		
		download_list.setAdapter(download_list_adapter);
		
		String CATALOG_ID= getIntent().getBundleExtra("DownloadList_Bundle").getString("CATALOG_ID");
		RequestParams params = new RequestParams();
		params.put("MONTH", CATALOG_ID);
		JulyUp_RestClient.post_Session(MyDownloadListActivity.this,Urls.GetFiles_URL, params,
				new TextHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1,
							String arg2) {
						System.out.println("files   is：  "+arg2);
						FilesBeen model = GsonUtil.getGson().fromJson(arg2, FilesBeen.class);
						my_download_title.setText(model.getResult().get(0).getYEAR()+"--"+model.getResult().get(0).getMONTH());
						download_list_adapter.SetData(model);
						
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1,String arg2, Throwable arg3) {
						Toast.makeText(getApplicationContext(), "请求数据失败，请检查网络信号！",Toast.LENGTH_SHORT).show();
						
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}
				});
	}
	
	@Click({R.id.donwnload_back})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.donwnload_back:
			this.finish();
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
		super.onBackPressed();
	}
}
