package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.adapter.UploadFinishListAdapter;
import com.mayi.julyup.adapter.Upload_Unfinished_list_Adapter;
import com.mayi.julyup.been.Upload_finish_Been;
import com.mayi.julyup.been.Upload_finish_Been.Data;
import com.mayi.julyup.been.Upload_unfinish_Been;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.util.GsonUtil;
/**
 * @author sondon
 *
 */
@EActivity(R.layout.upload_finished)
public class UploadTaskFinishActivity extends BaseActivity {
	@ViewById
	ListView upload_finished_list;
	@Bean
	UploadFinishListAdapter adapter;
	
	@AfterViews
	void init(){
		// 初始化适配器
		upload_finished_list.setAdapter(adapter);
		// 发送请求
		JulyUp_RestClient.post_Session(getApplicationContext(),Urls.Upload_Finish_URL, null, MyResponse);
		upload_finished_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Upload_finish_Been.Data model=(Upload_finish_Been.Data) adapter.getItem(arg2);
						Intent intent =new Intent(UploadTaskFinishActivity.this,Upload_Tasket_Img_Search_Show_.class);
						intent.putExtra("taskid", model.getTASK_ID());
						intent.putExtra("is_finish", true);
						startActivity(intent);
					}
				});
	}
	// http请求回调监听
		TextHttpResponseHandler MyResponse = new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] header, String arg2) {
				
				Upload_finish_Been model = GsonUtil.getGson().fromJson(arg2,
						Upload_finish_Been.class);
				if (model.isSuccess()) {
					adapter.Put_DataToList(model.getResult());
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {

			}
		};

}
