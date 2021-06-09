package com.demons.pay.base.net.config;

import android.content.Context;

import com.demons.pay.base.net.constants.KeyConstants;
import com.demons.pay.base.util.SpUtil;

/**
 * 主机域名配置
 *
 * @author demons
 */
public class Api {

    public volatile static Api retrofitManager;
    public Context context;

    public void init(Context context) {
        this.context = context;
    }

    public static Api getInstance() {
        if (retrofitManager == null) {
            synchronized (Api.class) {
                if (retrofitManager == null) {
                    retrofitManager = new Api();
                }
            }
        }
        return retrofitManager;
    }

    public String getHostUrl() {
        String address = SpUtil.getString(context, KeyConstants.HOST_ADDRESS, "azeysc.natappfree.cc");
        return "http://" + address;
    }
}
