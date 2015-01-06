package com.mayi.julyup.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayi.julyup.been.FaqListViewBeen;
import com.mayi.julyup.listviewItem.FaqListViewItem;
import com.mayi.julyup.listviewItem.FaqListViewItem_;

@EBean
public class FaqListAdapter extends BaseAdapter {
	@RootContext
	Context context;
	ArrayList<FaqListViewBeen> mylist=new ArrayList<FaqListViewBeen>();
	
	public void AddDataToList(List<FaqListViewBeen> list){
		if(mylist.size()>0){
			mylist.clear();
		}
		mylist.addAll(list);
	}
	
	@Override
	public int getCount() {
//		return mylist.size();
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return mylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		FaqListViewBeen model=mylist.get(position);
		FaqListViewItem faqlistviewItem=null;
		FaqListViewBeen model=null;
		
		if(convertView==null){
			faqlistviewItem=FaqListViewItem_.build(context);
		}else{
			faqlistviewItem=(FaqListViewItem) convertView;
		}
		faqlistviewItem.Bind(model, position);
		
		return faqlistviewItem;
	}

}
