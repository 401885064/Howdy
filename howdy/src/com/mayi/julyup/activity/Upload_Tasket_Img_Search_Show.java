package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.adapter.Upload_Tasket_Img_Search_Adapter;
import com.mayi.julyup.been.RequestBeen;
import com.mayi.julyup.been.Upload_Tasket_Img_Search_Show_Been;
import com.mayi.julyup.configs.Contents;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.pictures.PublishedActivity;
import com.mayi.julyup.util.GsonUtil;
import com.mayi.julyup.util.ToastUtils;

@EActivity(R.layout.upload_tasket_img_search_show)
public class Upload_Tasket_Img_Search_Show extends BaseActivity {
	@ViewById
	ListView upload_tasket_img_search_listview;
	@Bean
	Upload_Tasket_Img_Search_Adapter adapter;
	@ViewById
	Button unfinished_upload;
	@ViewById
	TextView unfinished_check;
	@ViewById
	TextView up_back;
	@AfterViews
	void init(){
//		if(Contents.is_finish){
//			unfinished_upload.setVisibility(View.GONE);
//			unfinished_check.setVisibility(View.GONE);
//		}
		//设置适配器
		upload_tasket_img_search_listview.setAdapter(adapter);
		
		//发送请求
		RequestParams params=new RequestParams();
		params.put("TASK_ID", Contents.Task_ID);
		JulyUp_RestClient.post_Session(getApplicationContext(),Urls.pic_search, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				Log.e(TAG, "上传任务图片查询 ："+arg2);
				final Upload_Tasket_Img_Search_Show_Been model=GsonUtil.getGson().fromJson(arg2, Upload_Tasket_Img_Search_Show_Been.class);
				if(!model.isSuccess()){
					ToastUtils.show(context, model.getMessage());
				}
				
				adapter.Put_DataToList(model.getResult());
				//图片点击事件
				upload_tasket_img_search_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						String[] img_src=new String[model.getResult().size()];
						for(int i=0;i<model.getResult().size();i++){
							img_src[i]=model.getResult().get(i).getSRC();
						}
						Bundle bundle=new Bundle();
						bundle.putStringArray("img_src", img_src);
						bundle.putInt("index", arg2);
						openActivity(GalleryUrlActivity.class,bundle);
					}
				});
				
				//删除图片
				upload_tasket_img_search_listview.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, final int position, long arg3) {
						
						//未审核前可以进行删除
						if("图片上传".equals(Contents.taskid_status)){
							AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Tasket_Img_Search_Show.this); 
//						    builder.setIcon(R.drawable.logo);  
					           builder.setTitle("你确定要删除这张图片吗？");  
					           builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
					               public void onClick(DialogInterface dialog, int whichButton) {  
					                   //这里添加点击确定后的逻辑  
					            	   RequestParams params=new RequestParams();
					            	   params.put("KEYS", model.getResult().get(position).getIMAGE_ID());
					            	   JulyUp_RestClient.post_Session(context, Urls.DeleterImage_URL, params, new TextHttpResponseHandler() {
										
										@Override
										public void onSuccess(int arg0, Header[] arg1, String json) {
											RequestBeen model=GsonUtil.getGson().fromJson(json,RequestBeen.class);
											if(model.isSuccess()){
												adapter.removeData(position);
											}else{
												ToastUtils.show(context, "删除失败！");
											}
										}
										@Override
										public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
											ToastUtils.show(context, "网络异常，删除失败");
										}
									});
					               }  
					           });  
					           builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
					               public void onClick(DialogInterface dialog, int whichButton) {  
					                   //这里添加点击取消后的逻辑  
					               }  
					           });  
					           builder.create().show();  
						}
						//审核未通过，重新上传
						if("图片审核".equals(Contents.taskid_status)&&"2".equals(model.getResult().get(position).getSTATUS_VALUE())){
							
							AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Tasket_Img_Search_Show.this); 
//						    builder.setIcon(R.drawable.logo);  
					           builder.setTitle("需要重新上传这张图片吗？");  
					           builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
					               public void onClick(DialogInterface dialog, int whichButton) {  
					                   //这里添加点击确定后的逻辑  
					            		Contents.upload_task_id=Contents.Task_ID;
					    				if("".equals(Contents.upload_task_id)){
					    					ToastUtils.show(context, "没有需要上传的任务！");
					    				return ;
					    				}
					    				Intent intent = new Intent(Upload_Tasket_Img_Search_Show.this,
					    						PublishedActivity.class);
					    				startActivity(intent);
					               }  
					           });  
					           builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
					               public void onClick(DialogInterface dialog, int whichButton) {  
					                   //这里添加点击取消后的逻辑  
					               }  
					           });  
					           builder.create().show();  
						}
						return false;
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			ToastUtils.show(getApplicationContext(), "获取数据失败！");
			}
		});
		
	}
	
	@Click({R.id.unfinished_upload,R.id.unfinished_check,R.id.up_back})
	void onClick(View v){
		switch (v.getId()) {
		case R.id.unfinished_upload:
			Intent intent =new Intent(Upload_Tasket_Img_Search_Show.this,PublishedActivity.class);
			startActivity(intent);
			break;
		case R.id.unfinished_check:
			RequestParams params=new RequestParams();
			params.put("TASK_ID", Contents.Task_ID);
			JulyUp_RestClient.post_Session(getApplicationContext(),Urls.UploadTaskCheck_URL, params,  new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, String arg2) {
					Log.e(TAG, "上传任务提交审核："+arg2);
					RequestBeen model=GsonUtil.getGson().fromJson(arg2, RequestBeen.class);
					if(!model.isSuccess()){
						ToastUtils.show(context, model.getMessage());
					}else{
						ToastUtils.show(context, "提交成功！");
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
					ToastUtils.show(context, "提交失败！");
				}
			});
			break;
		case R.id.up_back:
			this.finish();
			break;
		default:
			break;
		}
	}
	
}
