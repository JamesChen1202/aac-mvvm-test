package com.example.cn_chen.aac;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cn_chen on 2018/3/12.
 */

public class MyLifeCycleService extends LifecycleService {
//    public MyLifeCycleService(LifecycleOwner lifecycleOwner) {
//        super();
//        lifecycleOwner.getLifecycle().addObserver(this);
//
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("[MyLifeCycleService]", " onStartCommand!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("[MyLifeCycleService]", " onBind!");
        return super.onBind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("[MyLifeCycleService]", " onStart!");
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        Log.d("[MyLifeCycleService]", " onCreate!");
        super.onCreate();
    }
}
