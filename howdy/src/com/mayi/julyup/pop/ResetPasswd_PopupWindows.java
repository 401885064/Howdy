/**
 * 2014-8-15下午7:04:44
 */
package com.mayi.julyup.pop;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.pictures.PictureActivity;
import com.mayi.julyup.pictures.PublishedActivity;
import com.mayi.julyup.util.MD5;
import com.mayi.julyup.util.ToastUtils;

/**
 * @ClassName: G
 * @package com.mayi.july.pop
 * @author mayi
 * @date 2014-8-15 下午7:04:44
 * @Description: TODO(修改密码)
 * @version 1.0
 *
 */

public class ResetPasswd_PopupWindows extends PopupWindow {
	Context context;
	public ResetPasswd_PopupWindows(final Context mContext, View parent,final String name) {
		this.context=mContext;
		View view = View.inflate(mContext, R.layout.resetpasswd, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_ins));

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.CENTER, 0, 0);
		update();
		//初始化控件
		final  EditText old_passwd=(EditText) view.findViewById(R.id.old_passwd);
		final EditText new_passwd_1 = (EditText) view.findViewById(R.id.new_passwd_1);
		final EditText new_passwd_2 = (EditText) view.findViewById(R.id.new_passwd_2);
		Button reset_passwd_pop_cancle = (Button) view.findViewById(R.id.reset_passwd_pop_cancle);
		Button resetpasswd_pop_ok = (Button) view.findViewById(R.id.resetpasswd_pop_ok);
		
		//监控
		resetpasswd_pop_ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String passwd_1=new_passwd_1.getText().toString();
				String passwd_2=new_passwd_2.getText().toString();
				String oldpasswd=old_passwd.getText().toString();
				if(passwd_1.equals(passwd_2)){
					RequestParams params=new RequestParams();
					params.put("USERNAME", name);
					params.put("PASSWORD", MD5.getMD5(passwd_1));
					params.put("OLD_PASSWORD", MD5.getMD5(oldpasswd));
					JulyUp_RestClient.post_Session(context,Urls.ModifyPwd_URL, params, new TextHttpResponseHandler() {
						
						@Override
						public void onSuccess(int arg0, Header[] arg1, String arg2) {
							
						}
						@Override
						public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
							
						}
					});
				}else{
					ToastUtils.show(mContext, "两次密码不一致！");
				}
				
				dismiss();
			}
		});
		
		reset_passwd_pop_cancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

	}
}
