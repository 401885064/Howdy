/**
 * 2014-8-31上午9:42:44
 */
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
import com.mayi.julyup.adapter.TASK_ExpandListAdapter;
import com.mayi.julyup.been.FilesDir_Year;
import com.mayi.julyup.been.Upload_unfinish_Been;
import com.mayi.julyup.configs.Contents;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.pictures.PublishedActivity;
import com.mayi.julyup.util.AlertDialogUtil;
import com.mayi.julyup.util.GsonUtil;
import com.mayi.julyup.util.LogUtil;
import com.mayi.julyup.util.ToastUtils;

/**
 * @ClassName: Upload_Unfinished
 * @package com.mayi.julyup.activity
 * @author mayi
 * @date 2014-8-31 上午9:42:44
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
@EActivity(R.layout.upload_unfinished)
public class Upload_Unfinished extends BaseActivity {
	// @ViewById
	// ListView upload_unfinished_list;
	@ViewById
	TextView back;
	@ViewById
	TextView up_uploadimg;
	@ViewById
	ExpandableListView download_listview;
	@Bean
	TASK_ExpandListAdapter task_expand_adapter;
	public Dialog dialog = null;
	// @Bean
	// Upload_Unfinished_list_Adapter adapter;
	Handler myhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// ToastUtils.show(getApplicationContext(), "请求完成！");
				// 将所有项设置成默认展开
//				int groupCount = download_listview.getCount();
				download_listview.expandGroup(0);
				// for (int i=0; i<groupCount; i++) {
				// download_listview.expandGroup(i);
				// };
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

				break;

			default:
				break;
			}
		};
	};

	@AfterViews
	void init() {
		dialog = AlertDialogUtil.createLoadingDialog(this, "正在请求数据...");
		// 设置系统自带下拉箭头
		download_listview.setGroupIndicator(null);
		// 头项被点击事件
		download_listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView listview, View arg1,
					int arg2, long arg3) {
				// Toast.makeText(getApplicationContext(),
				// "setOnGroupClickListener", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		// 子项被点击事件
		download_listview.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView listview, View view,
					int groupPosition, int childPosition, long arg4) {
				// Toast.makeText(getApplicationContext(),
				// "setOnChildClickListener", Toast.LENGTH_SHORT).show();
				TASK_ExpandListAdapter myadapter = (TASK_ExpandListAdapter) listview
						.getExpandableListAdapter();

				Upload_unfinish_Been.Data model = myadapter.GetMonth()
						.get(groupPosition).getResult().get(childPosition);
				// 上传图片未审核详情页面
				Intent intent = new Intent(Upload_Unfinished.this,
						Upload_Tasket_Img_Search_Show_.class);
				Contents.Task_ID = model.getTASK_ID();
				if (!"0".equals(model.getSTATUS_VALUE())
						&& !"1".equals(model.getSTATUS_VALUE())) {
					Contents.is_finish = true;
				} else {
					Contents.is_finish = false;
				}
				Contents.Task_ID = model.getTASK_ID();
				Contents.taskid_status = model.getSTATUS();
				Log.e(TAG, model.getTASK_ID());
				startActivity(intent);
				return false;
			}
		});

		if (!dialog.isShowing()) {
			dialog.show();
		}
		// 发送年份请求
		JulyUp_RestClient.post_Session(getApplicationContext(),
				Urls.Get_Task_Year3, null, new TextHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						ToastUtils.show(getApplicationContext(),
								"网络请求失败，请稍后重试！");
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						Log.e(TAG, "下载任务单   年 ：" + arg2);
						FilesDir_Year model = GsonUtil.getGson().fromJson(arg2,
								FilesDir_Year.class);
						// Years = model.getResult();
						task_expand_adapter.SetYears(model.getResult());
						final ArrayList<Upload_unfinish_Been> months = new ArrayList<Upload_unfinish_Been>();
						//发送月份请求
						for (int i = 0; i < model.getResult().size(); i++) {
							RequestParams params = new RequestParams();
							params.put("CATALOG_ID", model.getResult().get(i)
									.getCATALOG_ID());

							JulyUp_RestClient.post_Session(
									getApplicationContext(), Urls.Request_PIC,
									params, new TextHttpResponseHandler() {
										@Override
										public void onSuccess(int arg0,
												Header[] arg1, String arg2) {
											LogUtil.e(TAG, "上传任务 ：" + arg2);
											Upload_unfinish_Been model = GsonUtil
													.getGson()
													.fromJson(
															arg2,
															Upload_unfinish_Been.class);
											for (int i = 0; i < model
													.getResult().size(); i++) {
												if (model.getResult().get(i)
														.getSTATUS_VALUE()
														.equals("0")) {
													Contents.upload_task_id = model
															.getResult().get(i)
															.getTASK_ID();
												}
											}
											months.add(model);
											
											if (dialog.isShowing()) {
												dialog.dismiss();
											}
											download_listview.setAdapter(task_expand_adapter);
											myhandler.sendEmptyMessageDelayed(1, 300);
										}

										@Override
										public void onFailure(int arg0,
												Header[] arg1, String arg2,
												Throwable arg3) {
											Toast.makeText(
													getApplicationContext(),
													"请求月份失败",
													Toast.LENGTH_SHORT).show();
											if (dialog.isShowing()) {
												dialog.dismiss();
											}
										}
									});
						}
						task_expand_adapter.SetMonth(months);
						
					}
				});

	};
	
	@Click({ R.id.back, R.id.up_uploadimg })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.up_uploadimg:
			if ("".equals(Contents.upload_task_id)) {
				ToastUtils.show(context, "暂时没有需要上传的任务！");
				return;
			}
			Intent intent = new Intent(Upload_Unfinished.this,
					PublishedActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
