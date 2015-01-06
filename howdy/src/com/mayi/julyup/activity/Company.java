package com.mayi.julyup.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.pop.ResetPasswd_PopupWindows;

@EActivity(R.layout.company)
public class Company extends BaseActivity {
	@ViewById(R.id.company_back)
	TextView company_back;
	@ViewById(R.id.company_reset_passwd)
	TextView company_reset_passwd;

	@Click({ R.id.company_back, R.id.company_reset_passwd })
	void onButtClick(View v) {
		switch (v.getId()) {
		case R.id.company_back:
			finish();
			break;
		case R.id.company_reset_passwd:
			String name=perference_getStringValue("");
			new ResetPasswd_PopupWindows(Company.this,findViewById(R.id.company_layout),name);
			break;
		default:
			break;
		}
	}
}
