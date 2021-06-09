package com.demons.pay.base.net.http;

import android.content.Context;

import com.demons.pay.base.net.constants.KeyConstants;
import com.demons.pay.base.util.SpUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * BaseInterceptor
 */

public class BaseInterceptor implements Interceptor {
    private Context context;

    public BaseInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = SpUtil.getString(context, KeyConstants.TOKEN, "");
        Request request = original.newBuilder()
                .header(KeyConstants.TOKEN, token)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
