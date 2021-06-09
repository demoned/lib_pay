package com.demons.payment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demons.pay.base.manager.PayManager;
import com.google.gson.Gson;


/**
 * MainActivity
 *
 * @author demons
 */
public class MainActivity extends AppCompatActivity implements PayManager.RouteResponseListener {

    private TransData transData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        findViewById(R.id.fab1).setOnClickListener(this::onClickEvent);
        findViewById(R.id.fab2).setOnClickListener(this::onClickEvent);
        findViewById(R.id.fab3).setOnClickListener(this::onClickEvent);
        findViewById(R.id.fab4).setOnClickListener(this::onClickEvent);
        /**
         * 获取数据,必须设置此监听
         */
        PayManager.getInstance().setRouteResponseListener(this);

        transData = new TransData();
    }

    @Override
    public void responseFromCcbSuccess(String appName, String transId, String resultInfoCode,
                                       String resultMsg, String transData) {

    }

    @Override
    public void responseFromCcbFail() {

    }

    @Override
    public void responseFromUnionPaySuccess(Bundle bundle) {

    }

    @Override
    public void responseFromUnionPayFail(String reason) {

    }

    public void onClickEvent(View view) {
        int id = view.getId();
        if (id == R.id.fab1) {
            PayManager.getInstance().getCcbRoute(MainActivity.this, "建行收单应用", PayManager.Constants.TransIdConstants.PAYMENT,
                    new Gson().toJson(transData), PayManager.Constants.RequestCcbConstants.PAYMENT_CODE);
        } else if (id == R.id.fab2) {
            PayManager.getInstance().getCcbRoute(MainActivity.this, "建行收单应用", PayManager.Constants.TransIdConstants.AGGREGATE_SCANNING_PAYMENT,
                    new Gson().toJson(transData), PayManager.Constants.RequestCcbConstants.AGGREGATE_SCANNING_PAYMENT_CODE);
        } else if (id == R.id.fab3) {
            PayManager.getInstance().getUnionPayRoute(MainActivity.this, "");
        } else if (id == R.id.fab4) {
            PayManager.getInstance().getUnionPayRoute(MainActivity.this, "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayManager.getInstance().release();
    }
}
