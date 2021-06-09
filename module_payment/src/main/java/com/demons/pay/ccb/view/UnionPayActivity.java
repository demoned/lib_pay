package com.demons.pay.ccb.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.demons.pay.R;
import com.demons.pay.base.manager.PayManager;
import com.demons.pay.base.mvvm.BaseMvvmActivity;
import com.demons.pay.base.net.constants.KeyConstants;
import com.demons.pay.base.util.LogUtils;
import com.demons.pay.ccb.mvvm.factory.PaymentViewModelFactory;
import com.demons.pay.ccb.mvvm.viewmodel.UnionPayViewModel;
import com.demons.pay.databinding.ActivityUnionPayBinding;


/**
 * 银联支付
 *
 * @author demons
 */
public class UnionPayActivity extends BaseMvvmActivity<ActivityUnionPayBinding, UnionPayViewModel> {
    protected static final String TAG = UnionPayActivity.class.getSimpleName();

    @Override
    public int onBindLayout() {
        return R.layout.activity_union_pay;
    }

    @Override
    public void initView() {
        setTransparentBg();
        showTransLoadingView(true);
    }

    @Override
    public void initData() {
        String unionPayInfo = getIntent().getStringExtra(KeyConstants.UNION_PAY_INFO);
        PayManager.getInstance().routeUnionPayBundleView(PayManager.Constants.ROUTE_UNION_PAY_PACK_NAME, this, unionPayInfo);
    }

    @Override
    public Class<UnionPayViewModel> onBindViewModel() {
        return UnionPayViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return PaymentViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public int onBindVariableId() {
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 6/3/21 加入结果上传的接口
        switch (resultCode) {
            case Activity.RESULT_CANCELED:
                String reason = data.getStringExtra("reason");
                if (reason != null) {
                    PayManager.getInstance().getRouteResponseListener().responseFromUnionPayFail(reason);
                    LogUtils.d(TAG, "失败原因：" + reason);
                }
                break;
            case Activity.RESULT_OK:
                Bundle bundle = data.getExtras();
                PayManager.getInstance().getRouteResponseListener().responseFromUnionPaySuccess(bundle);
                LogUtils.d(TAG, "接收的数据为：" + data.getDataString());
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void finish() {
        showTransLoadingView(false);
        super.finish();
    }
}
