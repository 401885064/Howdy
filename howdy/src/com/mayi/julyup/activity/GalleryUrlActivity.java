package com.mayi.julyup.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.truba.touchgallery.GalleryWidget.BasePagerAdapter.OnItemChangeListener;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.UrlPagerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mayi.julyup.R;

public class GalleryUrlActivity extends Activity {

    private GalleryViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_show_main);
        
        String[] urls =getIntent().getExtras().getStringArray("img_src");
        int index=getIntent().getExtras().getInt("index");
        List<String> items = new ArrayList<String>();
        Collections.addAll(items, urls);

        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, items);
        pagerAdapter.setOnItemChangeListener(new OnItemChangeListener()
		{
			@Override
			public void onItemChange(int currentPosition)
			{
//				Toast.makeText(GalleryUrlActivity.this, "Current item is " + currentPosition, Toast.LENGTH_SHORT).show();
			}
		});
        
        mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(index);
        
        //返回
        findViewById(R.id.up_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GalleryUrlActivity.this.finish();
			}
		});
    }

}