package com.mayi.julyup.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.mayi.julyup.R;

@EActivity(R.layout.upload_history_info)
public class UploadHistoryInfo extends BaseActivity {
	@ViewById(R.id.upload_history_info_cancle)
	TextView upload_history_info_cancle;
	@ViewById(R.id.upload_history_info_time)
	TextView upload_history_info_time;
	@ViewById(R.id.upload_history_info_gridview)
	GridView upload_history_info_gridview;
	
	@Click({R.id.upload_history_info_cancle})
	void OnButt_Click(View v){
		switch (v.getId()) {
		case R.id.upload_history_info_cancle:
			finish();
			break;
		default:
			break;
		}
	}
}
