package com.demons.pay.base.view.refresh.contract;

/**
 * 上拉加载更多接口
 */
public interface PushContract {
    /**
     * 手指上滑下滑的回调
     *
     * @param enable
     */
    void onPushEnable(boolean enable);

    /**
     * 手指松开的回调
     */
    void onLoadMore();
}
