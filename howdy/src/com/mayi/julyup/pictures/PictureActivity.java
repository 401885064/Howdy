package com.mayi.julyup.pictures;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.activity.BaseActivity;

/**
 * 目录列表 PictureActivity.java
 * 
 * @author mayi 2014-8-8 下午11:29:57
 * 
 */
public class PictureActivity extends BaseActivity implements View.OnClickListener{
	// ArrayList<Entity> dataList;//用来装载数据源的列表
	// 用来装载数据源的列表
	List<ImageBucket> dataList;
	GridView gridView;
	// 数据源的列表适配器 自定义的适配器
	ImageBucketAdapter adapter;
	
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap; // 默认图片 +
	TextView image_bucket_cancle_text;// 取消

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);
		// 默认图片
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		// 初始化图片文件夹目录列表
		adapter = new ImageBucketAdapter(PictureActivity.this, dataList);
		image_bucket_cancle_text = (TextView) findViewById(R.id.image_bucket_cancle_text);

		// 取消点击事件
		image_bucket_cancle_text.setOnClickListener(this);

		// 适配器
		gridView.setAdapter(adapter);
		// 适配器监听
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 传递数据
				Intent intent = new Intent(PictureActivity.this,
						ImageGridActivity.class);
				intent.putExtra(PictureActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				// 跳转到图片列表
				startActivity(intent);
				//关闭当前activity
//				PictureActivity.this.finish();
			}

		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		openActivity(PublishedActivity.class);
		PictureActivity.this.finish();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_bucket_cancle_text:
			openActivity(PublishedActivity.class);
			PictureActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
