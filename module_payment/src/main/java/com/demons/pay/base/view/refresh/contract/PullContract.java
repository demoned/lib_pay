package com.demons.pay.base.view.refresh.contract;

/**
 * 下拉刷新的接口
 */
public interface PullContract {
    /**
     * 手指上滑下滑的回调
     *
     * @param enable
     */
    void onPullEnable(boolean enable);

    /**
     * 手指松开的回调
     */
    void onRefresh();
}
