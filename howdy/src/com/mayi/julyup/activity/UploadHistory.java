/**
 * 2014-8-26上午12:23:32
 */
package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.widget.Button;

import com.mayi.julyup.R;

/**
 * @ClassName: UploadHistory
 * @package com.mayi.julyup.activity
 * @author mayi
 * @date 2014-8-26 上午12:23:32
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
@EActivity(R.layout.upload_history)
public class UploadHistory extends BaseActivity {
	@ViewById
	Button upload_unfinished;
	@ViewById
	Button upload_complete;

	@AfterViews
	void init() {

	}

	@Click({ R.id.upload_complete, R.id.upload_unfinished })
	void Click(View v) {
		switch (v.getId()) {
		case R.id.upload_complete:
			openActivity(UploadTaskFinishActivity_.class);
			break;
		case R.id.upload_unfinished:
			openActivity(Upload_Unfinished_.class);
			break;
		default:
			break;
		}
	}
}
