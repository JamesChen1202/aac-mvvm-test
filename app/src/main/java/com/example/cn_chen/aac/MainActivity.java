package com.example.cn_chen.aac;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cn_chen.aac.model.TimerData;
import com.example.cn_chen.aac.viewmodel.MainActModel;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTimerTextMvvm;
    private TextView mTimerText;
    private TextView mHelloWorldMvvm;
    private Button mBtn;
    private int mCounter;
    private MainActModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("[MainActivity]", " onCreate!");
        setContentView(R.layout.activity_main);

        mTimerTextMvvm = findViewById(R.id.textView1);
        mTimerText = findViewById(R.id.textView2);
        mHelloWorldMvvm = findViewById(R.id.textView3);
        mCounter = 0;
        mBtn = findViewById(R.id.mybutton);
        if(mTimerTextMvvm == null) {
            Log.d("[MainActivity]", "mTimerText is null");
        }


        Log.d("[MainActivity]", " initialization!");
        runMVVMTimer();

        runTimer();

        registerMVVMbutton();

    }

    private void registerMVVMbutton() {
        // create a click listener to manipulate custom data model
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setTimerData();
            }
        });

        // monitor the change of custom model - TimerData
        mViewModel.getTimerData().observe(this, new Observer<TimerData>() {
            @Override
            public void onChanged(@Nullable TimerData timerData) {
                mHelloWorldMvvm.setText("MVVM button test! "+timerData.getCount()+"");
            }
        });

    }

    private void runMVVMTimer() {
        /*
        * The created ViewModel is associated with the given scope and will be retained
        * as long as the scope is alive (e.g. if it is an activity, until it is finished
        * or process is killed).
        *
        * */
        mViewModel = ViewModelProviders.of(this).get(MainActModel.class);
        //if(mViewModel == null) mViewModel = new MainActModel();  // life cycle not associated with MainActivity.java

        // new Observer to observe a Long value
        Observer<Long> myObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                mTimerTextMvvm.setText("MVVMTimer: "+aLong+"");
                Log.d("[MainActivity]", "[onChanged] timer");
            }
        };

        // bind Long value observer to getElapsedTime() which return <LiveData>Long
        mViewModel.getElapsedTime().observe(this, myObserver);
    }

    private void runTimer() {
        java.util.Timer timer = new java.util.Timer(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimerText.setText("Timer: "+mCounter+"");
                        mCounter++;
                    }
                });
            }
        }, 0, 1000);
    }
}
