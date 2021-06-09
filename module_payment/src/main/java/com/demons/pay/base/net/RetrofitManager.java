package com.demons.pay.base.net;

import android.content.Context;
import android.widget.Toast;

import com.demons.pay.base.manager.PayManager;
import com.demons.pay.base.net.config.Api;
import com.demons.pay.base.net.http.BaseInterceptor;
import com.demons.pay.base.util.LogUtils;
import com.demons.pay.base.util.SSLContextUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitManager
 * 网络请求管理器
 */
public class RetrofitManager {
    public static RetrofitManager retrofitManager;
    public static Context mContext;
    private Retrofit mRetrofit;
    public String TOKEN;

    private RetrofitManager() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //添加token
        okHttpBuilder.addInterceptor(new BaseInterceptor(mContext));
        //添加日志拦截相关
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("网络请求：-----", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (PayManager.isDebug()) {
            okHttpBuilder.addInterceptor(interceptor);
        }
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            okHttpBuilder.sslSocketFactory(socketFactory);
        }
//        okHttpBuilder.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        try {
            mRetrofit = new Retrofit.Builder().client(okHttpBuilder.build()).baseUrl(Api.getInstance().getHostUrl()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        } catch (Exception e) {
            Toast.makeText(RetrofitManager.mContext, "当前域名不是有效主机域名!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 创建一个公共服务
     *
     * @return
     */
    public CommonService getCommonService() {
        return mRetrofit.create(CommonService.class);
    }


    public static void release() {
        if (null != retrofitManager) {
            retrofitManager = null;
        }
    }
}