package com.demons.pay.base.net;

import com.demons.pay.base.net.entity.BaseResponse;
import com.demons.pay.base.net.entity.PaymentRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommonService {
    /**
     * 消费提交的接口
     *
     * @param paymentRequest
     * @return
     */
    @POST("/api/order/registration/payment")
    Observable<BaseResponse> consumptionOrder(@Body PaymentRequest paymentRequest);

    @POST("/api/order/registration/refund")
    Observable<BaseResponse> refundOrder(@Body PaymentRequest newsDetail);
}
