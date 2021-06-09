package com.demons.payment;

import android.app.Application;

import com.demons.pay.base.manager.PayManager;

/**
 * 主入口
 *
 * @author demons
 */
public class PayApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PayManager.getInstance().init(this,true);
    }

}
