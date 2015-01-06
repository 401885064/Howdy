package com.mayi.julyup.listviewItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.been.FaqListViewBeen;

@EViewGroup(R.layout.faq_listview_item)
public class FaqListViewItem extends LinearLayout{
	@ViewById(R.id.faq_listview_title)
	TextView faq_listview_title;
	@ViewById(R.id.faq_listview_contents)
	TextView faq_listview_contents;
	Context context;
	
	public FaqListViewItem(Context context) {
		super(context);
		this.context=context;
	}
	
	public void Bind(FaqListViewBeen model,int position){
//		faq_listview_title.setText(getResources().getString(R.string.faq_listview_title));
//		faq_listview_contents.setText(getResources().getString(R.string.faq_listview_contents));
		
//		faq_listview_title.setText("123");
//		faq_listview_contents.setText("1452");
	}
}
