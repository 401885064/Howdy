package com.mayi.julyup.adapter;

import java.util.ArrayList;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayi.julyup.been.Upload_finish_Been;
import com.mayi.julyup.listviewItem.UploadFinishListItem;
import com.mayi.julyup.listviewItem.UploadFinishListItem_;

@EBean
public class  UploadFinishListAdapter extends BaseAdapter {
	@RootContext
	Context context;
	ArrayList<Upload_finish_Been.Data> datas = new ArrayList<Upload_finish_Been.Data>();

	public void Put_DataToList(ArrayList<Upload_finish_Been.Data> arrayList) {
		datas.clear();
		if (arrayList.size() != 0 || arrayList != null) {
			datas.addAll(arrayList);
		}
		notifyDataSetChanged();
	}

	public ArrayList<Upload_finish_Been.Data> getDatas(){
		return datas;
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UploadFinishListItem faqlistviewItem = null;
		Upload_finish_Been.Data model = datas.get(position);

		if (convertView == null) {
			faqlistviewItem =UploadFinishListItem_.build(context);
		} else {
			faqlistviewItem = (UploadFinishListItem) convertView;
		}
		faqlistviewItem.Bind(model, position);

		return faqlistviewItem;
	}


}
