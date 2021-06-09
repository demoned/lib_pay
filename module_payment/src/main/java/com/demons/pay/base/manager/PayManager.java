package com.demons.pay.base.manager;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.demons.pay.base.net.RetrofitManager;
import com.demons.pay.base.net.config.Api;
import com.demons.pay.base.net.constants.KeyConstants;
import com.demons.pay.base.util.CrashHandler;
import com.demons.pay.base.util.DisplayUtil;
import com.demons.pay.base.util.LogUtils;
import com.demons.pay.base.util.NetUtil;
import com.demons.pay.base.util.ToastUtil;
import com.demons.pay.ccb.view.CcbActivity;
import com.demons.pay.ccb.view.UnionPayActivity;

import java.util.Map;

/**
 * 支付业务服务类
 *
 * @author demons
 */
public class PayManager {
    private static boolean isDebug;

    private static class CcbManagerHolder {
        private static PayManager ccbManager = new PayManager();
    }

    public static PayManager getInstance() {
        return CcbManagerHolder.ccbManager;
    }

    public void init(Application application, boolean isDebug) {
        setDebug(isDebug);
        Api.getInstance().init(application);
        RetrofitManager.init(application);
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (isDebug) {
            LogUtils.init(true);
        } else {
            //异常捕捉初始化
            initCrashHandler(application);
        }
        DisplayUtil.init(application);
        ToastUtil.init(application);
        NetUtil.init(application);
    }

    public void routeCcbBundleView(@NonNull String packageName, Activity activity, String appName, String transId
            , String transData, String authIndex, String authCode, int responseCode) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = activity.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == packageinfo) {
            ToastUtil.showToast("没找到对应的应用！");
            activity.finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("appName", appName);
            intent.putExtra("transId", transId);
            intent.putExtra("transData", transData);
            intent.putExtra("authIndex", authIndex);
            intent.putExtra("authCode", authCode);
            ComponentName cn = new ComponentName(packageName, Constants.ROUTE_CCB_CLASS_NAME);
            intent.setComponent(cn);
            activity.startActivityForResult(intent, responseCode);
        }
    }

    public void routeUnionPayBundleView(@NonNull String packageName, Activity activity, String json) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = activity.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == packageinfo) {
            ToastUtil.showToast("没找到对应的应用！");
            activity.finish();
        } else {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(json)) {
                ToastUtil.showToast("入参不能为空！");
                activity.finish();
                return;
            }
            Map maps = (Map) JSON.parse(json);
            for (Object map : maps.entrySet()) {
                String key = String.valueOf(((Map.Entry) map).getKey());
                String value = (String) ((Map.Entry) map).getValue();
                intent.putExtra(key, value);
            }
            ComponentName cn = new ComponentName(packageName, Constants.ROUTE_UNION_PAY_CLASS_NAME);
            intent.setComponent(cn);
            activity.startActivityForResult(intent, 0);
        }
    }


    public void getCcbRoute(Activity activity, String appName, String transId, String transData, int responseCode) {
        Intent intent = new Intent(activity.getApplicationContext(), CcbActivity.class);
        intent.putExtra(KeyConstants.APP_NAME, appName);
        intent.putExtra(KeyConstants.TRANS_ID, transId);
        intent.putExtra(KeyConstants.TRANS_DATA, transData);
        intent.putExtra(KeyConstants.RESPONSE_CODE, responseCode);
        activity.startActivityForResult(intent, responseCode);

    }

    public void getUnionPayRoute(Activity activity, String unionPayInfo) {
        Intent intent = new Intent(activity.getApplicationContext(), UnionPayActivity.class);
        intent.putExtra(KeyConstants.UNION_PAY_INFO, unionPayInfo);
        activity.startActivity(intent);
    }

    public interface Constants {
        String ROUTE_CCB_PACK_NAME = "com.ccb.smartpos.bankpay";
        String ROUTE_CCB_CLASS_NAME = "com.ccb.smartpos.bankpay.ui.MainActivity";

        String ROUTE_UNION_PAY_PACK_NAME = "com.landicorp.android.unionpay";
        String ROUTE_UNION_PAY_CLASS_NAME = "com.landicorp.android.unionpay.MainActivity";

        interface TransIdConstants {
            /**
             * 支付
             */
            String PAYMENT = "消费";
            /**
             * 撤销
             */
            String REVOKE = "撤销";
            /**
             * 退货
             */
            String RETURN_GOODS = "退货";
            /**
             * 查询交易明细
             */
            String QUERY_TRANSACTION_DETAILS = "查询交易明细";
            /**
             * 查询交易汇总
             */
            String QUERY_TRANSACTION_SUMMARY = "查询交易汇总";
            /**
             * 聚合扫码支付
             */
            String AGGREGATE_SCANNING_PAYMENT = "聚合扫码支付";
            /**
             * 二维码收款
             */
            String QR_CODE_COLLECTION = "二维码收款";
            /**
             * 补登
             */
            String SUPPLEMENT = "按唯一订单号查询";
        }

        /**
         * 建行操作请求码
         */
        interface RequestCcbConstants {
            /**
             * 支付
             */
            int PAYMENT_CODE = 0x1101;
            /**
             * 撤销
             */
            int REVOKE_CODE = 0x1102;
            /**
             * 退货
             */
            int RETURN_GOODS_CODE = 0x1103;
            /**
             * 查询交易明细
             */
            int QUERY_TRANSACTION_DETAILS_CODE = 0x1104;
            /**
             * 查询交易汇总
             */
            int QUERY_TRANSACTION_SUMMARY_CODE = 0x1105;
            /**
             * 聚合扫码支付
             */
            int AGGREGATE_SCANNING_PAYMENT_CODE = 0x1106;
            /**
             * 二维码收款
             */
            int QR_CODE_COLLECTION_CODE = 0x1107;
            /**
             * 补登
             */
            int SUPPLEMENT_CODE = 0x1108;
        }

        interface TransNameConstants {
            /**
             * 签到
             */
            String SIGN_IN = "签到";
            /**
             * 消费
             */
            String PAYMENT = "消费";
            /**
             * 消费撤销
             */
            String REVOKE = "消费撤销";
            /**
             * 退货
             */
            String RETURN_GOODS = "退货";
        }

        interface RequestUnionPayConstants {
            /**
             * 签到
             */
            int SIGN_IN_CODE = 0x1201;
            /**
             * 消费
             */
            int CONSUMPTION_CODE = 0x1202;
            /**
             * 消费撤销
             */
            int CONSUMPTION_CANCELLATION_CODE = 0x1203;
            /**
             * 扫码付款
             */
            int PAYMENT_BY_SCANNING_CODE_CODE = 0x1204;
            /**
             * 退货
             */
            int RETURN_GOODS_CODE = 0x1205;
            /**
             * 交易查询
             */
            int TRANSACTION_INQUIRY_CODE = 0x1206;
        }

        /**
         * 按照对应⽀付⽅式上送编码
         * 微信主扫⽀付 ：101
         * 微信被扫⽀付 ：102
         * 微信⼩程序⽀付： 103
         * ⽀付宝主扫⽀付： 201
         * ⽀付宝被扫⽀付 ：202
         * 云闪付主扫⽀付 ：301
         * 云闪付被扫⽀付 ：302
         * 银⾏卡 ：001
         * 预付卡： 002
         * 积分 ：003
         * 现⾦ ：004
         * 商城礼券： 005
         * 其他：000
         */
        interface PaymentMethodCode {

        }

        /**
         * 按照对应⽀付渠道上送编码建⾏商⼾应⽤：
         * 001 银联扫码付：
         * 002 银商移动⽀付：
         * 003 其他：000
         */
        interface PayChannelCode {

        }

        /**
         * 按照对应终端来源上送编码
         * ⾃营系统：001
         * 收银系统：002
         * tteampos系统：003
         */

        interface ResponseConstants {
            String APP_NAME = "appName";
            String TRANS_ID = "transId";
            String RESULT_CODE = "resultCode";
            String RESULT_MSG = "resultMsg";
            String TRANS_DATA = "transData";
        }
    }


    private RouteResponseListener routeResponseListener;

    public RouteResponseListener getRouteResponseListener() {
        return routeResponseListener;
    }

    public void setRouteResponseListener(RouteResponseListener routeResponseListener) {
        this.routeResponseListener = routeResponseListener;
    }

    public interface RouteResponseListener {
        /**
         * 建行成功回调
         *
         * @param appName
         * @param transId
         * @param resultInfoCode
         * @param resultMsg
         * @param transData
         */
        void responseFromCcbSuccess(String appName, String transId, String resultInfoCode,
                                    String resultMsg, String transData);

        /**
         * 建行失败回调
         */
        void responseFromCcbFail();

        /**
         * 银联成功回调
         *
         * @param bundle
         */
        void responseFromUnionPaySuccess(Bundle bundle);

        /**
         * 银联失败回调
         *
         * @param reason
         */
        void responseFromUnionPayFail(String reason);
    }

    private void initCrashHandler(Application application) {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(application);
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public void release() {
        setRouteResponseListener(null);
    }
}
