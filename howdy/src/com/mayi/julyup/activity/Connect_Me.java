package com.mayi.julyup.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mayi.julyup.R;

@EActivity(R.layout.connect_me)
public class Connect_Me extends BaseActivity {
	@ViewById(R.id.grid_cancle_text)
	TextView grid_cancle_text;
	@ViewById(R.id.connect_me_text)
	TextView connect_me_text;
	@ViewById(R.id.call_me)
	Button call_me;

	@Click({ R.id.grid_cancle_text, R.id.connect_me_text, R.id.call_me })
	void onclick(View v) {
		switch (v.getId()) {
		case R.id.grid_cancle_text:
			finish();
			break;
		case R.id.connect_me_text:

			break;
		case R.id.call_me:
			Intent intent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:110"));
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
