package com.demons.pay.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.demons.pay.R;


/**
 * NoDataView
 * 暂无数据的视图
 */
public class NoDataView extends RelativeLayout {

    private final RelativeLayout mRlNoDataRoot;
    private final ImageView mImgNoDataView;

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_no_data, this);
        mRlNoDataRoot = findViewById(R.id.rl_no_data_root);
        mImgNoDataView = findViewById(R.id.img_no_data);
    }

    public void setNoDataBackground(@ColorRes int colorResId) {
        mRlNoDataRoot.setBackgroundColor(getResources().getColor(colorResId));
    }

    public void setNoDataView(@DrawableRes int imgResId) {
        mImgNoDataView.setImageResource(imgResId);
    }
}