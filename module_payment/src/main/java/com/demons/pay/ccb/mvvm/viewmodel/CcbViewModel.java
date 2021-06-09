package com.demons.pay.ccb.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.demons.pay.base.event.SingleLiveEvent;
import com.demons.pay.base.mvvm.viewmodel.BaseViewModel;
import com.demons.pay.base.net.entity.BaseResponse;
import com.demons.pay.base.net.entity.CcbCommonBean;
import com.demons.pay.base.net.entity.PaymentRequest;
import com.demons.pay.base.util.NetUtil;
import com.demons.pay.ccb.mvvm.model.CcbModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 建行支付视图模型
 *
 * @author demons
 */
public class CcbViewModel extends BaseViewModel<CcbModel> {
    protected ObservableField<PaymentRequest> paymentRequestObservableField = new ObservableField<>();
    protected SingleLiveEvent<CcbCommonBean> consumptionOrderLiveEvent;

    public CcbViewModel(@NonNull Application application, CcbModel model) {
        super(application, model);
    }

    public void consumptionOrder(CcbCommonBean ccbCommonBean) {
        if (!NetUtil.checkNetToast()) {
            postShowNetWorkErrViewEvent(true);
            return;
        }
        mModel.consumptionOrder(getPaymentRequestObservableField().get()).subscribe(new Observer<BaseResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                postShowInitLoadViewEvent(true);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                getConsumptionOrderLiveEvent().postValue(ccbCommonBean);
            }

            @Override
            public void onError(Throwable e) {
                postShowInitLoadViewEvent(false);
            }

            @Override
            public void onComplete() {
                postShowInitLoadViewEvent(false);
            }
        });
    }

    public ObservableField<PaymentRequest> getPaymentRequestObservableField() {
        return paymentRequestObservableField;
    }

    public SingleLiveEvent<CcbCommonBean> getConsumptionOrderLiveEvent() {
        return consumptionOrderLiveEvent = createLiveData(consumptionOrderLiveEvent);
    }
}
