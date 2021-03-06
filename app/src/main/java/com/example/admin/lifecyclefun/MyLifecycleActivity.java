package com.example.admin.lifecyclefun;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Admin on 2018-05-11.
 */

public class MyLifecycleActivity implements LifecycleObserver {

    private static final String TAG = "MyLifecycleActivity";
    Context mCon;

     MyLifecycleActivity(Context con) {
         this.mCon = con;
        ((AppCompatActivity)con).getLifecycle().addObserver(this);

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void myOnCreate(){
        Log.e(TAG, "Called onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void myOnStart(){
        Log.e(TAG, "Called onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void myOnResume(){
        Log.e(TAG, "Called onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void myOnStop(){
        Log.e(TAG, "Called onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void myOnDestroy(){
        Log.e(TAG, "Called onDestroy");

        // Remember to clear the reference to LifeCycle aware component
        // This avoid memory leaks of the activity
        ((AppCompatActivity) mCon).getLifecycle().removeObserver(this);
        Log.e(TAG, "---- Removed observer here--- ");

        Lifecycle.State currentState = ((AppCompatActivity)mCon).getLifecycle().getCurrentState();
        Log.e(TAG, "Current state: " + currentState);
    }

}
