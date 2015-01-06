package com.mayi.julyup.service;


import com.mayi.julyup.util.ToastUtils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class NetStateService extends Service {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//            	ToastUtils.show(context, "网络状态已经改变");
//                Log.i("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
//                    Log.i("mark", "当前网络名称：" + name);
                } else {
//                    Log.i("mark", "没有可用网络");
                    ToastUtils.show(context, "网络信号不好，请检查网络！");
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("mReceiver", "注册广播....");
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册广播
        registerReceiver(mReceiver, mFilter);
    }
    
    @Override
    public void onDestroy() {
        Log.e("mReceiver", "注销广播....");
        //注销广播
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}