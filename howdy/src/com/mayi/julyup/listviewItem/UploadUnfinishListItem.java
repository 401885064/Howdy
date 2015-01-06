package com.mayi.julyup.listviewItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.been.Upload_unfinish_Been;

@EViewGroup(R.layout.upload_unfinished_list_item)
public class UploadUnfinishListItem extends LinearLayout {
	@ViewById
	TextView upload_unfinished_list_item_name;
	@ViewById
	TextView upload_unfinished_list_item_statue;

	@ViewById
	TextView upload_unfinished_list_creat_time;
	@ViewById
	TextView upload_unfinished_list_last_modify_time;

	@ViewById
	TextView upload_unfinished_list_item_remark;

	Context context;

	public UploadUnfinishListItem(Context context) {
		super(context);
		this.context = context;
	}
	
	
	public void Bind(Upload_unfinish_Been.Data model, int position) {
		
		upload_unfinished_list_item_name.setText(model.getTASK_NAME());
		upload_unfinished_list_item_statue.setText(model.getSTATUS());

		upload_unfinished_list_creat_time.setText(model.getCREATE_TIME());
		upload_unfinished_list_last_modify_time.setText(model.getLAST_MODIFY_TIME());
		
		upload_unfinished_list_item_remark.setText(model.getREMARK());
	}
}
