package com.demons.pay.base.event.common;

import com.demons.pay.base.event.BaseEvent;

/**
 * BaseActivityEvent
 */
public class BaseActivityEvent<T> extends BaseEvent<T> {
    public BaseActivityEvent(int code) {
        super(code);
    }
}
