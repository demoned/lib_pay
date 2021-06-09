package com.demons.pay.base.fragmentation.fragmentation;

import android.view.MotionEvent;

import com.demons.pay.base.fragmentation.fragmentation.anim.FragmentAnimator;

/**
 * ISupportActivity
 */

public interface ISupportActivity {
    SupportActivityDelegate getSupportDelegate();

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    FragmentAnimator onCreateFragmentAnimator();

    void post(Runnable runnable);

    void onBackPressed();

    void onBackPressedSupport();

    boolean dispatchTouchEvent(MotionEvent ev);
}
