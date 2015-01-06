package com.mayi.julyup.listviewItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.been.Upload_Tasket_Img_Search_Show_Been;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

@EViewGroup(R.layout.upload_tasket_img_search_item)
public class Upload_Tasket_Img_Search_Item extends LinearLayout {
	@ViewById
	ImageView imageshow;
	@ViewById
	TextView image_name;
	@ViewById
	TextView image_statue;
	@ViewById
	TextView image_creat_time;
	@ViewById
	TextView image_last_modify_time;
	@ViewById
	TextView image_relation_code;

	Context context;
	// uml初始化
	ImageLoader imageload = ImageLoader.getInstance();
	DisplayImageOptions options = Options.getListOptions();

	public Upload_Tasket_Img_Search_Item(Context context) {
		super(context);
		this.context = context;
	}

	public void Bind(Upload_Tasket_Img_Search_Show_Been.Data model, int position) {
		imageload.displayImage(model.getSRC(),imageshow, options);
		image_name.setText(model.getIMAGE_NAME());
		image_statue.setText("【" + model.getSTATUS() + "】");

		image_creat_time.setText("创建时间:" + model.getCREATE_TIME());
		image_last_modify_time.setText("最后修改时间：" + model.getLAST_MODIFY_TIME());

		image_relation_code.setText("关联码:" + model.getRELATION_CODE());
	}
}
