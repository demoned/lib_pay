package com.demons.pay.base.event;

/**
 * RequestCode
 */
public interface RequestCode {
    interface Main {
        //1000开始
    }

    interface News {
        //2000开始
    }

    interface Find {
        //3000开始
    }

    interface Me {
        //4000开始
        int NEWS_TYPE_ADD = 4000;
    }
}
