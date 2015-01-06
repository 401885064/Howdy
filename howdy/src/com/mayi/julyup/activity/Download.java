package com.mayi.julyup.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.adapter.MyExpandListAdapter;
import com.mayi.julyup.been.FilesDir_Month;
import com.mayi.julyup.been.FilesDir_Year;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.util.AlertDialogUtil;
import com.mayi.julyup.util.GsonUtil;
import com.mayi.julyup.util.ToastUtils;

@EActivity(R.layout.my_download)
public class Download extends BaseActivity {
	@ViewById(R.id.donwnload_back)
	TextView donwnload_back;
	@ViewById
	ExpandableListView download_listview;
	@Bean
	MyExpandListAdapter adapter;
	public Dialog dialog=null;
	Handler myhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
//				ToastUtils.show(getApplicationContext(), "请求完成！");
				   //将所有项设置成默认展开
//				   int groupCount = download_listview.getCount();
				   download_listview.expandGroup(0);
//				   for (int i=0; i<groupCount; i++) {
//					   download_listview.expandGroup(i);
//				       };
				break;

			default:
				break;
			}
		};
	};
	
	
	@AfterViews
	void init() {
		dialog=AlertDialogUtil.createLoadingDialog(this, "正在请求数据...");
		// 设置系统自带下拉箭头
		download_listview.setGroupIndicator(null);
		// 头项被点击事件
		download_listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView listview, View arg1,
					int arg2, long arg3) {
//				Toast.makeText(getApplicationContext(),
//						"setOnGroupClickListener", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		// 子项被点击事件
		download_listview.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView listview, View view,
					int groupPosition, int childPosition, long arg4) {
//				Toast.makeText(getApplicationContext(),
//						"setOnChildClickListener", Toast.LENGTH_SHORT).show();
				MyExpandListAdapter myadapter=(MyExpandListAdapter) listview.getExpandableListAdapter();
				Intent intent =new Intent(Download.this,MyDownloadListActivity_.class);
				Bundle bundle=new Bundle();
				bundle.putString("CATALOG_ID",  myadapter.GetMonth().get(groupPosition).getResult().get(childPosition).getCATALOG_ID());
				intent.putExtra("DownloadList_Bundle", bundle);
				startActivity(intent);
				return false;
			}
		});
		
		//请求年份
		 if(!dialog.isShowing()){
			   dialog.show();
		   }
		JulyUp_RestClient.post_Session(getApplicationContext(),
				Urls.FilesDir_Year_URL, null, new TextHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						Log.e("Download  info:", arg2);
						FilesDir_Year model = GsonUtil.getGson().fromJson(arg2,FilesDir_Year.class);
						System.out.println(model.getTotal());
						adapter.SetYears(model.getResult());
						final ArrayList<FilesDir_Month> months=new ArrayList<FilesDir_Month>();
						for(int i=0;i<model.getResult().size();i++){
							
							RequestParams params = new RequestParams();
							params.put("CATALOG_ID", model.getResult().get(i).getCATALOG_ID());
							//请求月份
							JulyUp_RestClient.post_Session(getApplicationContext(),
									Urls.FilesDir_Month_URL, params,
									new TextHttpResponseHandler() {
										@Override
										public void onSuccess(int arg0, Header[] arg1,
												String arg2) {
											System.out.println("month: json  is：  "+arg2);
											FilesDir_Month model = GsonUtil.getGson().fromJson(arg2, FilesDir_Month.class);
											months.add(model);
											
											if(dialog.isShowing()){
												   dialog.dismiss();
											   }
											//设置适配器
											download_listview.setAdapter(adapter);
											myhandler.sendEmptyMessageDelayed(1,300);
										}
										@Override
										public void onFailure(int arg0, Header[] arg1,String arg2, Throwable arg3) {
											Toast.makeText(getApplicationContext(), "请求月份失败",Toast.LENGTH_SHORT).show();
											if(dialog.isShowing()){
												   dialog.dismiss();
											   }
										}
									});
						}
						adapter.SetMonth(months);
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						Toast.makeText(getApplicationContext(), "请求年份失败",Toast.LENGTH_SHORT).show();
						 if(dialog.isShowing()){
							   dialog.dismiss();
						   }
					}
				});
	}

	@Click({ R.id.donwnload_back })
	void OnButt_Click(View v) {
		switch (v.getId()) {
		case R.id.donwnload_back:
			finish();
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
