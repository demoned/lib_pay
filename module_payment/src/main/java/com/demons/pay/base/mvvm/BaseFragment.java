package com.demons.pay.base.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.demons.pay.R;
import com.demons.pay.base.event.common.BaseFragmentEvent;
import com.demons.pay.base.fragmentation.SupportFragment;
import com.demons.pay.base.mvvm.view.IBaseView;
import com.demons.pay.base.util.LogUtils;
import com.demons.pay.base.util.NetUtil;
import com.demons.pay.base.view.LoadingInitView;
import com.demons.pay.base.view.LoadingTransView;
import com.demons.pay.base.view.NetErrorView;
import com.demons.pay.base.view.NoDataView;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * BaseFragment
 */
public abstract class BaseFragment extends SupportFragment implements IBaseView {
    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected RxAppCompatActivity mActivity;
    protected View mView;
    protected TextView mTxtTitle;
    protected Toolbar mToolbar;

    protected NetErrorView mNetErrorView;
    protected NoDataView mNoDataView;
    protected LoadingInitView mLoadingInitView;
    protected LoadingTransView mLoadingTransView;

    private ViewStub mViewStubToolbar;
    protected RelativeLayout mViewStubContent;
    private ViewStub mViewStubInitLoading;
    private ViewStub mViewStubTransLoading;
    private ViewStub mViewStubNoData;
    private ViewStub mViewStubError;
    private boolean isViewCreated = false;
    private boolean isViewVisable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RxAppCompatActivity) getActivity();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_root1, container, false);
        initCommonView(mView);
        initView(mView);
        initListener();
        return mView;
    }

    public void initCommonView(View view) {
        mViewStubToolbar = view.findViewById(R.id.view_stub_toolbar);
        mViewStubContent = view.findViewById(R.id.view_stub_content);
        mViewStubInitLoading = view.findViewById(R.id.view_stub_init_loading);
        mViewStubTransLoading = view.findViewById(R.id.view_stub_trans_loading);
        mViewStubNoData = view.findViewById(R.id.view_stub_nodata);
        mViewStubError = view.findViewById(R.id.view_stub_error);

        if (enableToolbar()) {
            mViewStubToolbar.setLayoutResource(onBindToolbarLayout());
            View viewTooBbar = mViewStubToolbar.inflate();
            initTooBar(viewTooBbar);
        }
        initConentView(mViewStubContent);
    }

    public void initConentView(ViewGroup root) {
        LayoutInflater.from(mActivity).inflate(onBindLayout(), root, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        //?????????????????????????????????????????????????????????????????????
        if (enableLazyData()) {
            lazyLoad();
        } else {
            initData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisable = isVisibleToUser;
        //?????????????????????????????????????????????
        if (enableLazyData() && isViewVisable) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        //??????????????????????????????,????????????onCreateView???????????????????????????,???????????????
        LogUtils.v("MYTAG", "lazyLoad start...");
        LogUtils.v("MYTAG", "isViewCreated:" + isViewCreated);
        LogUtils.v("MYTAG", "isViewVisable" + isViewVisable);
        if (isViewCreated && isViewVisable) {
            initData();
            //??????????????????,????????????,??????????????????
            isViewCreated = false;
            isViewVisable = false;
        }
    }

    //????????????????????????
    public boolean enableLazyData() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void initTooBar(View view) {
        mToolbar = view.findViewById(R.id.toolbar_root);
        mTxtTitle = view.findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
            mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });
        }
        if (mTxtTitle != null) {
            mTxtTitle.setText(getToolbarTitle());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(BaseFragmentEvent<T> event) {
    }

    public abstract int onBindLayout();

    public abstract void initView(View view);

    public void initView() {
    }

    public abstract void initData();

    public abstract String getToolbarTitle();

    public void initListener() {
    }

    @Override
    public void finishActivity() {
        mActivity.finish();
    }

    public boolean enableToolbar() {
        return false;
    }

    public int onBindToolbarLayout() {
        return R.layout.common_toolbar;
    }

    public void showInitLoadView(boolean show) {
        if (mLoadingInitView == null) {
            View view = mViewStubInitLoading.inflate();
            mLoadingInitView = view.findViewById(R.id.view_init_loading);
        }
        mLoadingInitView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingInitView.loading(show);
    }


    public void showNetWorkErrView(boolean show) {
        if (mNetErrorView == null) {
            View view = mViewStubError.inflate();
            mNetErrorView = view.findViewById(R.id.view_net_error);
            mNetErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.checkNetToast()) {
                        return;
                    }
                    showNetWorkErrView(false);
                    initData();
                }
            });
        }
        mNetErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    public void showNoDataView(boolean show) {
        if (mNoDataView == null) {
            View view = mViewStubNoData.inflate();
            mNoDataView = view.findViewById(R.id.view_no_data);
        }
        mNoDataView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showNoDataView(boolean show, int resid) {
        showNoDataView(show);
        if (show) {
            mNoDataView.setNoDataView(resid);
        }
    }

    public void showTransLoadingView(boolean show) {
        if (mLoadingTransView == null) {
            View view = mViewStubTransLoading.inflate();
            mLoadingTransView = view.findViewById(R.id.view_trans_loading);
        }
        mLoadingTransView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoadingTransView.loading(show);
    }

}
