package com.demons.pay.ccb.view;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.demons.pay.R;
import com.demons.pay.base.manager.PayManager;
import com.demons.pay.base.mvvm.BaseMvvmActivity;
import com.demons.pay.base.net.constants.KeyConstants;
import com.demons.pay.base.net.entity.CcbCommonBean;
import com.demons.pay.base.net.entity.PaymentRequest;
import com.demons.pay.base.net.entity.ResponseCcbTransData;
import com.demons.pay.base.util.DateUtil;
import com.demons.pay.base.util.LogUtils;
import com.demons.pay.base.util.SignUtils;
import com.demons.pay.ccb.mvvm.factory.PaymentViewModelFactory;
import com.demons.pay.ccb.mvvm.viewmodel.CcbViewModel;
import com.demons.pay.databinding.ActivityCcbBinding;
import com.google.gson.Gson;

import java.util.Date;

/**
 * 建行支付
 *
 * @author demons
 */
public class CcbActivity extends BaseMvvmActivity<ActivityCcbBinding, CcbViewModel> {
    protected static final String TAG = CcbActivity.class.getSimpleName();
    private PaymentRequest paymentRequest;

    @Override
    public int onBindLayout() {
        return R.layout.activity_ccb;
    }

    @Override
    public void initView() {
        setTransparentBg();
        showTransLoadingView(true);
    }

    @Override
    public void initData() {
        String appName = getIntent().getStringExtra(KeyConstants.APP_NAME);
        String transId = getIntent().getStringExtra(KeyConstants.TRANS_ID);
        String transData = getIntent().getStringExtra(KeyConstants.TRANS_DATA);
        int responseCode = getIntent().getIntExtra(KeyConstants.RESPONSE_CODE, 0);
        PayManager.getInstance().routeCcbBundleView(PayManager.Constants.ROUTE_CCB_PACK_NAME, this, appName, transId, transData,
                "", "", responseCode);
        if (transId.equals(PayManager.Constants.TransIdConstants.PAYMENT)
                || transId.equals(PayManager.Constants.TransIdConstants.AGGREGATE_SCANNING_PAYMENT)) {
            paymentRequest = new PaymentRequest();
        }
    }

    @Override
    public Class<CcbViewModel> onBindViewModel() {
        return CcbViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return PaymentViewModelFactory.getInstance(getApplication());
    }

    @Override
    public void initViewObservable() {
        mViewModel.getConsumptionOrderLiveEvent().observe(this, new Observer<CcbCommonBean>() {
            @Override
            public void onChanged(@Nullable CcbCommonBean ccbCommonBean) {
                PayManager.getInstance().getRouteResponseListener().responseFromCcbSuccess(ccbCommonBean.getAppName(), ccbCommonBean.getTransId(),
                        ccbCommonBean.getResultInfoCode(), ccbCommonBean.getResultMsg(), ccbCommonBean.getTransDat());
                finish();
            }
        });
    }

    @Override
    public boolean enableToolbar() {
        return false;
    }

    @Override
    public int onBindVariableId() {
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String appName = data.getStringExtra(PayManager.Constants.ResponseConstants.APP_NAME);
        String transId = data.getStringExtra(PayManager.Constants.ResponseConstants.TRANS_ID);
        String resultInfoCode = data.getStringExtra(PayManager.Constants.ResponseConstants.RESULT_CODE);
        String resultMsg = data.getStringExtra(PayManager.Constants.ResponseConstants.RESULT_MSG);
        String transData = data.getStringExtra(PayManager.Constants.ResponseConstants.TRANS_DATA);
        if (TextUtils.isEmpty(transData)) {
            return;
        }
        CcbCommonBean ccbCommonBean = new CcbCommonBean(appName, transId, resultInfoCode, resultMsg, transData);
        ResponseCcbTransData responseCcbTransData = new Gson().fromJson(transData, ResponseCcbTransData.class);
        if (transId.equals(PayManager.Constants.TransIdConstants.PAYMENT)) {
            initPaymentRequest(responseCcbTransData, "001", "001");
            mViewModel.getPaymentRequestObservableField().set(paymentRequest);
            mViewModel.consumptionOrder(ccbCommonBean);
        } else if (transId.equals(PayManager.Constants.TransIdConstants.AGGREGATE_SCANNING_PAYMENT)) {
            initPaymentRequest(responseCcbTransData, "001", "102");
            mViewModel.getPaymentRequestObservableField().set(paymentRequest);
            mViewModel.consumptionOrder(ccbCommonBean);
        }
        LogUtils.d(TAG, "接收的数据为：" + appName + "," + transId + "," + resultInfoCode + "," + resultMsg + "," + transData + ".");
        PayManager.getInstance().getRouteResponseListener().responseFromCcbSuccess(appName, transId, resultInfoCode, resultMsg, transData);
    }

    private void initPaymentRequest(ResponseCcbTransData responseCcbTransData, String pay_channel_code, String payment_method_code) {
        paymentRequest.setApp_id("jc5dff79705f45ff11");
        paymentRequest.setMch_id("1042052500000054");
        paymentRequest.setTimestamp(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmssNumber));
        paymentRequest.setStore_no("");
        paymentRequest.setChannel_mch_id(responseCcbTransData.getMerchantID());
        paymentRequest.setSource_code("003");
        paymentRequest.setPay_channel_code(pay_channel_code);
        paymentRequest.setPayment_method_code(payment_method_code);
        paymentRequest.setOut_trade_no(responseCcbTransData.getTraceNo());
        paymentRequest.setBank_order_no("");
        if (responseCcbTransData.getResCode().equals("00")) {
            paymentRequest.setStatus("SUCCESS");
        } else {
            paymentRequest.setStatus("FAIL");
        }
        paymentRequest.setTotal_amount(String.valueOf(Integer.parseInt(responseCcbTransData.getAmt())));
        paymentRequest.setDiscount_amount("");
        paymentRequest.setReceived_amount("");
        paymentRequest.setChange_amount("");
        paymentRequest.setService_charge("");
        paymentRequest.setCard_type("DEBIT");
        paymentRequest.setIssue(responseCcbTransData.getCardIssuer());
        paymentRequest.setBank_card_no(responseCcbTransData.getCardNo());
        paymentRequest.setCard_association_code("");
        paymentRequest.setTime_start(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmssNumber));
        paymentRequest.setTime_end(DateUtil.formatDate(new Date(), DateUtil.FormatType.yyyyMMddHHmmssNumber));
        paymentRequest.setTrace_no(responseCcbTransData.getTraceNo());
        paymentRequest.setTerminal_id(responseCcbTransData.getTerminalID());
        paymentRequest.setInvoice_id(responseCcbTransData.getBatchNo());
        paymentRequest.setInvoice_issuing_date(responseCcbTransData.getTransTime());
        paymentRequest.setCashier(responseCcbTransData.getOperID());
        paymentRequest.setGoods("");
        paymentRequest.setAttach("");
        paymentRequest.setSign_type("HMAC-SHA256");
        String sign = SignUtils.sha256_HMAC(new Gson().toJson(paymentRequest), "6b66114e1277e6d75d05e9387ba7f36f");
        paymentRequest.setSign(sign);
    }

    @Override
    public void finish() {
        showTransLoadingView(false);
        super.finish();
    }
}
