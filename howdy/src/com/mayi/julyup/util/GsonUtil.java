package com.mayi.julyup.util;

import com.google.gson.Gson;

public class GsonUtil {
    private static Gson mygson;

    private GsonUtil() {

    }

    public static Gson getGson() {
        synchronized (GsonUtil.class) {
            if (mygson == null) {
                mygson = new Gson();
            }
        }
        return mygson;
    }
}
