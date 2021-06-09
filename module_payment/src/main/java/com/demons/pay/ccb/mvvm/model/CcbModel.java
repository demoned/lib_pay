package com.demons.pay.ccb.mvvm.model;

import android.app.Application;

import com.demons.pay.base.mvvm.model.BaseModel;
import com.demons.pay.base.net.RetrofitManager;
import com.demons.pay.base.net.entity.BaseResponse;
import com.demons.pay.base.net.entity.PaymentRequest;
import com.demons.pay.base.net.http.RxAdapter;

import io.reactivex.Observable;


/**
 * 建行支付数据模型
 *
 * @author demons
 */
public class CcbModel extends BaseModel {

    public CcbModel(Application application) {
        super(application);
    }

    public Observable<BaseResponse> consumptionOrder(PaymentRequest paymentRequest) {
        return RetrofitManager.getInstance().getCommonService().consumptionOrder(paymentRequest)
                .compose(RxAdapter.schedulersTransformer())
                .compose(RxAdapter.exceptionTransformer());
    }
}
