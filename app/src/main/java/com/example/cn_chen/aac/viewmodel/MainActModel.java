package com.example.cn_chen.aac.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;
import android.util.Log;

import com.example.cn_chen.aac.MyLifeCycleService;
import com.example.cn_chen.aac.model.TimerData;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cn_chen on 2018/3/8.
 */

public class MainActModel extends ViewModel implements LifecycleObserver {
    private static final int ONE_SECOND = 1000;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();
    private MutableLiveData<TimerData> mMyTimerLiveData = new MutableLiveData<>();
    private TimerData mTimerData;
    private LifecycleService lifecycleService;

    private long mInitialTime;

    public MainActModel() {
        Log.d("[ViewModel]", " constructor!");
        if(lifecycleService == null) {
            lifecycleService = new MyLifeCycleService();
            lifecycleService.getLifecycle().addObserver(this);
            lifecycleService.onStart(null, 0);
        }

        mTimerData = new TimerData();
        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;

                // setValue() cannot be called from a background thread so post to main thread.
                //TODO post the new value with LiveData.postValue()
                mElapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void startup() {
        Log.d("[ViewModel]", " ON_START!");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void cleanup() {
        Log.d("[ViewModel]", " ON_STOP!");
    }

    @SuppressWarnings("unused")  // Will be used when step is completed
    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }

    public LiveData<TimerData> getTimerData() {
        return  mMyTimerLiveData;
    }

    public void setTimerData() {
        mTimerData.addCount();
        mMyTimerLiveData.postValue(mTimerData);
//        mTimerData.addCount();
//        mMyTimerLiveData.setValue(mTimerData);
    }
}
