/**
 * 2014-8-15下午7:04:44
 */
package com.mayi.julyup.pop;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mayi.julyup.R;
import com.mayi.julyup.pictures.PublishedActivity;

/**
 * @ClassName: Picture_PopupWindows
 * @package com.mayi.july.pop
 * @author mayi
 * @date 2014-8-15 下午7:04:44
 * @Description: TODO(选择图片来源:照相机或者相册)
 * @version 1.0
 */
public class Picture_PopupWindows extends PopupWindow implements View.OnClickListener {
	public PublishedActivity published;

	public Picture_PopupWindows(PublishedActivity published, View parent) {
		this.published = published;
		// 加载布局文件
		View view = View.inflate(published, R.layout.item_popupwindows, null);
		// 设置view的动画
		view.startAnimation(AnimationUtils.loadAnimation(published,R.anim.fade_ins));
		// 获取view中的控件
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		// 开始view的动画
		ll_popup.startAnimation(AnimationUtils.loadAnimation(published,R.anim.push_bottom_in_2));

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		// 添加下面的代码会出现阴影效果
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);

		// Updates the state of the popup window
		update();

		Button camera_butt 	= (Button) 	view.findViewById(R.id.item_popupwindows_camera);
		Button Photo_butt 	= (Button) 	view.findViewById(R.id.item_popupwindows_Photo);
		Button cancel_butt	= (Button) 	view.findViewById(R.id.item_popupwindows_cancel);

		camera_butt.setOnClickListener(this);
		Photo_butt.setOnClickListener(this);
		cancel_butt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 相机按钮
		case R.id.item_popupwindows_camera:
			this.published.camera();
			dismiss();
			break;
		// 相册按钮
		case R.id.item_popupwindows_Photo:
			this.published.photo();
			dismiss();
			break;
		// 取消按钮
		case R.id.item_popupwindows_cancel:
			dismiss();
			break;
		default:
			break;
		}
	}
}
