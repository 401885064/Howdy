package com.mayi.julyup.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mayi.julyup.R;
import com.mayi.julyup.been.RequestBeen;
import com.mayi.julyup.configs.Urls;
import com.mayi.julyup.https.JulyUp_RestClient;
import com.mayi.julyup.util.AlertDialogUtil;
import com.mayi.julyup.util.GsonUtil;
import com.mayi.julyup.util.MD5;
import com.mayi.julyup.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

@EActivity(R.layout.login)
public class LoginActivity extends BaseActivity {
	@ViewById(R.id.button_user_login)
	Button button_user_login;
	@ViewById(R.id.edittext_user_username)
	EditText edittext_user_username;
	@ViewById(R.id.edittext_user_pwd)
	EditText edittext_user_pwd;
	@ViewById(R.id.login_remember)
	CheckBox login_remember;
	boolean isRemember;
	public Dialog dialog=null;
	
	@AfterViews
	void init() {
		//Umeng
		MobclickAgent.updateOnlineConfig(context);
		
		dialog=AlertDialogUtil.createLoadingDialog(LoginActivity.this, "正在登陆...");
		isRemember=perference_getBooleanValue("isRemember");
		if(isRemember){
			String name = perference_getStringValue("username");
			String passwd = perference_getStringValue("passwd");
			//用户名和密码都不为空的情况下
			if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
				login_remember.setChecked(true);
				edittext_user_username.setText(name);
				edittext_user_pwd.setText(passwd);
			} else {
				login_remember.setChecked(false);
			}
		}
		login_remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isRemember = isChecked;
					}
				});
		}

	/**
	 * 控件点击事件
	 */
	@Click(R.id.button_user_login)
	void button_login_onclick() {
		String name = edittext_user_username.getText().toString().trim();
		String passwd = edittext_user_pwd.getText().toString().trim();

		// 判断是否为空
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(passwd)) {
			ToastUtils.show(getApplicationContext(), R.string.login_empty);
			return;
		}
		
		// 记住密码
		RememberPasswd(name, passwd);
		// 登陆请求
		Request_Login(name, passwd);
	}

	/**
	 * 记住密码
	 * 
	 * @param isremember
	 */
	void RememberPasswd(String name, String passwd) {
		perference_putStringValue("isRemember", isRemember);
		// 是否记住密码
		if (isRemember) {
			perference_putStringValue("passwd", passwd);
		} else {
			perference_putStringValue("passwd", "");
		}
	}
	
	/**
	 * 
	 * @Title: Request_Login
	 * @Description: TODO(发送登陆请求)
	 * @param @param name   用户名
	 * @param @param passwd 密码
	 * @return void    返回类型
	 * @throws
	 */
	public void Request_Login(final String name, final String passwd) {
		if(!dialog.isShowing()){
			dialog.show();
		}
		// 发送请求
		RequestParams params = new RequestParams();
		params.put("USERNAME", name);
		String md5_passwd= MD5.getMD5(passwd);
		params.put("PASSWORD", md5_passwd);
		// 登录请求响应
		JulyUp_RestClient.post_Session(getApplicationContext(),Urls.Login_URL, params,  new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String result) {
				System.out.println("result    :" + result);
				RequestBeen model = GsonUtil.getGson().fromJson(result,RequestBeen.class);
				//判断是否成功
				if (model.isSuccess()) {
					perference_putStringValue("username", name);
					Intent intent = new Intent(LoginActivity.this,MainActivity_.class);
					startActivity(intent);
				}else{
					showLongToast(model.getMessage());
				}
			   if(dialog.isShowing()){
				   dialog.dismiss();
			   }
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable throwable) {
//				Log.e(TAG, throwable.getMessage());
				ToastUtils.show(context, "登陆失败！");
			   if(dialog.isShowing()){
				   dialog.dismiss();
			   }
			}
		});
	}
}
