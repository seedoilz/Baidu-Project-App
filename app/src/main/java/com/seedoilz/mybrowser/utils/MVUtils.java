package com.seedoilz.mybrowser.utils;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;


public class MVUtils {

    private static MVUtils mInstance;
    private static MMKV mmkv;

    public MVUtils() {
        mmkv = MMKV.defaultMMKV();
    }

    public static MVUtils getInstance() {
        if (mInstance == null) {
            synchronized (MVUtils.class) {
                if (mInstance == null) {
                    mInstance = new MVUtils();
                }
            }
        }
        return mInstance;
    }


    public static void put(String key, Object object) {
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mmkv.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            mmkv.encode(key, (byte[]) object);
        } else {
            mmkv.encode(key, object.toString());
        }
    }

    public static Long getLong(String key) {
        return mmkv.decodeLong(key, 0L);
    }


    public static Boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

}

