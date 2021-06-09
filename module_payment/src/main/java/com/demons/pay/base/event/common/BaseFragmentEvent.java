package com.demons.pay.base.event.common;

import com.demons.pay.base.event.BaseEvent;

/**
 * BaseFragmentEvent
 */
public class BaseFragmentEvent<T> extends BaseEvent<T> {
    public BaseFragmentEvent(int code) {
        super(code);
    }
}
