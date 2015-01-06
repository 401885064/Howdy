package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mayi.julyup.R;
import com.mayi.julyup.adapter.MyExpandListAdapter;

@EActivity(R.layout.upload_history_listview)
public class UploadHistoryListView extends BaseActivity {
	@ViewById(R.id.uploadhistory_cancle_text)
	TextView uploadhistory_cancle_text;
	
	@ViewById(R.id.expandlistview)
	ExpandableListView expandlistview;
	@Bean
	MyExpandListAdapter expandadapter;
	
	@AfterViews
	void initView(){
		expandlistview.setAdapter(expandadapter);
		//设置系统自带下拉箭头
		expandlistview.setGroupIndicator(null); 
		expandlistview.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
//				 Toast.makeText(
//						 UploadHistory.this,
//	                        "你点击了" + parent.getExpandableListAdapter().getChild(groupPosition, childPosition),
//	                        Toast.LENGTH_SHORT).show();
				 	UploadHistoryListView.this.openActivity(UploadHistoryInfo_.class);
	                return false;
			}
		});
	}
	
	@Click({R.id.uploadhistory_cancle_text})
	void onclicl(View v){
		switch (v.getId()) {
		case R.id.uploadhistory_cancle_text:
			finish();
			break;
		case R.id.expandlistview:
			break;
		default:
			break;
		}
	}
}
