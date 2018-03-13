package com.example.cn_chen.aac.model;

/**
 * Created by cn_chen on 2018/3/8.
 */

public class TimerData implements MyTimer {
    private int mCount = 0;

    @Override
    public int addCount() {
        return mCount++;
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
