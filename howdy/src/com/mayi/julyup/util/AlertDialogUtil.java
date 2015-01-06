package com.mayi.julyup.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayi.julyup.R;

/**
 * 
 * @ClassName: AlertDialogUtil
 * @author 蚂蚁
 * @date 2014-8-11 下午1:54:30
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
public class AlertDialogUtil {
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) view
				.findViewById(R.id.loadingImageView);
		TextView tipTextView = (TextView) view
				.findViewById(R.id.id_tv_loadingmsg);// 提示文字

		// 使用ImageView显示动画
		final AnimationDrawable anim = (AnimationDrawable) spaceshipImage
				.getBackground();
		// 设置提示信息
		tipTextView.setText(msg);
		tipTextView.setTextColor(context.getResources().getColor(R.color.lightgrey));
		// 创建自定义样式dialog
		Dialog loadingDialog = new Dialog(context,R.style.CustomProgressDialog);
		// 不可以用“返回键”取消
//		loadingDialog.setCancelable(false);
		// 可以用“返回键”取消
		loadingDialog.setCancelable(true);
		// 设置布局
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		// 监听show事件
		loadingDialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				// TODO Auto-generated method stub
				System.out.println("anim start...");
				anim.start();
			}
		});
		// 监听dismiss事件
		loadingDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				System.out.println("anim stop...");
				anim.stop();
			}
		});

		return loadingDialog;
	}
}
