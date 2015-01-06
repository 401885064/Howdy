/**
 * 2014-8-31上午10:01:38
 */
package com.mayi.julyup.adapter;

import java.util.ArrayList;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayi.julyup.been.Upload_unfinish_Been;
import com.mayi.julyup.listviewItem.UploadUnfinishListItem;
import com.mayi.julyup.listviewItem.UploadUnfinishListItem_;

/**
 * @ClassName: Upload_Unfinished_list_Adapter
 * @package com.mayi.julyup.adapter
 * @author mayi
 * @date 2014-8-31 上午10:01:38
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
@EBean
public class Upload_Unfinished_list_Adapter extends BaseAdapter {

	@RootContext
	Context context;
	ArrayList<Upload_unfinish_Been.Data> datas = new ArrayList<Upload_unfinish_Been.Data>();

	public void Put_DataToList(ArrayList<Upload_unfinish_Been.Data> arrayList) {
//		datas.clear();
		if (arrayList.size() != 0 || arrayList != null) {
			datas.addAll(arrayList);
		}
		notifyDataSetChanged();
	}

	public ArrayList<Upload_unfinish_Been.Data> getDatas(){
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
		UploadUnfinishListItem faqlistviewItem = null;
		Upload_unfinish_Been.Data model = datas.get(position);

		if (convertView == null) {
			faqlistviewItem =UploadUnfinishListItem_.build(context);
		} else {
			faqlistviewItem = (UploadUnfinishListItem) convertView;
		}
		faqlistviewItem.Bind(model, position);

		return faqlistviewItem;
	}

}
