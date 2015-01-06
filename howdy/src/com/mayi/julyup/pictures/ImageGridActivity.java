package com.mayi.julyup.pictures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayi.julyup.R;
import com.mayi.julyup.activity.BaseActivity;
import com.mayi.julyup.pictures.ImageGridAdapter.TextCallback;

/**
 * 具体图片列表 Activity ImageGridActivity.java
 * 
 * @author mayi 2014-8-7 下午9:44:50
 * 
 */
public class ImageGridActivity extends BaseActivity implements View.OnClickListener{
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	// 用来装载数据源的列表
	List<ImageItem> dataList;
	GridView gridView;
	// ImageGrid 适配器
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button image_grid_ok_butt;// 完成
	TextView grid_cancle_text;// 取消

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择9张图片",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		grid_cancle_text = (TextView) findViewById(R.id.grid_cancle_text);

		grid_cancle_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// 获取Intent传过来的图片list
		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();

		image_grid_ok_butt = (Button) findViewById(R.id.image_grid_ok_butt);
		image_grid_ok_butt.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ImageGridActivity.this.finish();
	};

	private void initView() {
		// 获取控件
		gridView = (GridView) findViewById(R.id.gridview);
		// 定制当点击GridView 的时候出现的那个黄色背景
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 适配器
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);

		gridView.setAdapter(adapter);

		// Item监听
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				image_grid_ok_butt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 更新
				adapter.notifyDataSetChanged();
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_grid_ok_butt:
			ArrayList<String> list = new ArrayList<String>();
			Collection<String> c = adapter.map.values();
			Iterator<String> it = c.iterator();
			for (; it.hasNext();) {
				list.add(it.next());
			}

			if (Bimp.act_bool) {
				Intent intent = new Intent(ImageGridActivity.this,
						PublishedActivity.class);
				startActivity(intent);
				Bimp.act_bool = false;
			}
			for (int i = 0; i < list.size(); i++) {
				if (Bimp.drr.size() < 9) {
					Bimp.drr.add(list.get(i));
				}
			}
			
			//关闭activity，跳转到PublishedActivity
			ImageGridActivity.this.finish();
			openActivity(PublishedActivity.class);
			break;

		default:
			break;
		}
	}
}
