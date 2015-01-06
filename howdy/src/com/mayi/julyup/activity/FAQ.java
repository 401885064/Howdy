package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.adapter.FaqListAdapter;
import com.mayi.julyup.util.ToastUtils;

@EActivity(R.layout.faq)
public class FAQ extends BaseActivity {
	@ViewById(R.id.faq_back)
	TextView faq_back;
	@ViewById(R.id.faq_none)
	LinearLayout faq_none;
	@ViewById(R.id.faq_listview)
	ListView faq_listview;
	@Bean
	FaqListAdapter faq_adapter;

	@AfterViews
	void init() {
		faq_listview.setAdapter(faq_adapter);
		faq_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				ToastUtils.show(context, "被点击。。。");
			}
		});
		//显示提示信息
		showHint(true);
	}
	
	//显示提示信息
	void showHint(boolean IsHaveData){
		if(IsHaveData){
			faq_none.setVisibility(View.GONE);
			faq_listview.setVisibility(View.VISIBLE);
		}else{
			faq_none.setVisibility(View.VISIBLE);
			faq_listview.setVisibility(View.GONE);
		}
	}
	
	//点击事件
	@Click({ R.id.faq_back })
	void onButtClick(View v) {
		switch (v.getId()) {
		case R.id.faq_back:
			finish();
			break;
		default:
			break;
		}
	}
}
