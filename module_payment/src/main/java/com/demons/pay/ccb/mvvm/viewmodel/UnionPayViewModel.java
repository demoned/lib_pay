package com.demons.pay.ccb.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.demons.pay.base.mvvm.viewmodel.BaseViewModel;
import com.demons.pay.ccb.mvvm.model.UnionPayModel;

/**
 * 建行支付视图模型
 *
 * @author demons
 */
public class UnionPayViewModel extends BaseViewModel<UnionPayModel> {

    public UnionPayViewModel(@NonNull Application application, UnionPayModel model) {
        super(application, model);
    }
}
