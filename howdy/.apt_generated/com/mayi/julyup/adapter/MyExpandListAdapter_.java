//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.mayi.julyup.adapter;

import android.content.Context;

public final class MyExpandListAdapter_
    extends MyExpandListAdapter
{

    private Context context_;

    private MyExpandListAdapter_(Context context) {
        context_ = context;
        init_();
    }

    public static MyExpandListAdapter_ getInstance_(Context context) {
        return new MyExpandListAdapter_(context);
    }

    private void init_() {
        context = context_;
        init();
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
