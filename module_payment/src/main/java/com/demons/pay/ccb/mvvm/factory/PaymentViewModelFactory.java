package com.demons.pay.ccb.mvvm.factory;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demons.pay.ccb.mvvm.model.CcbModel;
import com.demons.pay.ccb.mvvm.model.UnionPayModel;
import com.demons.pay.ccb.mvvm.viewmodel.CcbViewModel;
import com.demons.pay.ccb.mvvm.viewmodel.UnionPayViewModel;

/**
 * 支付视图模型工厂
 *
 * @author demons
 */
public class PaymentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile PaymentViewModelFactory instance;
    private final Application mApplication;

    public static PaymentViewModelFactory getInstance(Application application) {
        if (instance == null) {
            synchronized (PaymentViewModelFactory.class) {
                if (instance == null) {
                    instance = new PaymentViewModelFactory(application);
                }
            }
        }
        return instance;
    }

    private PaymentViewModelFactory(Application application) {
        this.mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CcbViewModel.class)) {
            return (T) new CcbViewModel(mApplication, new CcbModel(mApplication));
        } else if (modelClass.isAssignableFrom(UnionPayViewModel.class)) {
            return (T) new UnionPayViewModel(mApplication, new UnionPayModel(mApplication));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
