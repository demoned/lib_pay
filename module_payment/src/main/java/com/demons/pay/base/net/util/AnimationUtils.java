package com.demons.pay.base.net.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 */
public class AnimationUtils {
    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToBottom() {
        TranslateAnimation hiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        hiddenAction.setDuration(300);
        return hiddenAction;
    }

    public static TranslateAnimation moveToLocation() {
        TranslateAnimation visibleAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        visibleAction.setDuration(300);
        return visibleAction;

    }
}
