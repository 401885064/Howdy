package com.mayi.julyup.https;

import java.util.List;

import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.mayi.julyup.activity.BaseActivity;

public class JulyUp_RestClient {
	/**
	 *http
	 */
	private final static AsyncHttpClient myClient = new AsyncHttpClient();

	private JulyUp_RestClient() {
	}

	public static AsyncHttpClient getHttpClient() {
		return myClient;
	}

	public static AsyncHttpClient getHttpClientSession(Context context) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		
//		if("".equals(BaseActivity.JSESSIONID)){
			List<Cookie> cookies=myCookieStore.getCookies();
			if (!cookies.isEmpty()) {
				for (Cookie cookie : cookies) {
					BaseActivity.JSESSIONID=cookie.getValue();
					Log.e("mayi", "JSESSIONID  :"+BaseActivity.JSESSIONID);
					}
//			}
		}
		myClient.setCookieStore(myCookieStore);
		return myClient;
	}
	
	//暂时不需要
//	public static void get(String url, RequestParams params,
//			AsyncHttpResponseHandler responseHandler) {
//		getHttpClient().get(url, params, responseHandler);
//	}

//	public static void post(String url, RequestParams params,
//			AsyncHttpResponseHandler responseHandler) {
//		getHttpClient().post(url, params, responseHandler);
//	}
	
	public static void get_Session(Context context,String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		getHttpClientSession(context).get(url, params, responseHandler);
	}

	public static void post_Session(Context context,String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		getHttpClientSession(context).post(url, params, responseHandler);
	}
}