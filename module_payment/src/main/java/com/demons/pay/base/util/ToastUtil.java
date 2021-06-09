package com.demons.pay.base.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 */
public class ToastUtil {
    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    public static void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resid) {
        Toast.makeText(context, context.getString(resid), Toast.LENGTH_SHORT)
                .show();
    }
}